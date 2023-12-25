package daniil.backend.service.impl

import daniil.backend.dto.user.*
import daniil.backend.entity.User
import daniil.backend.enums.UserRole
import daniil.backend.exception.UserAlreadyExistsException
import daniil.backend.exception.UserHasNoPermissionException
import daniil.backend.extension.getRole
import daniil.backend.extension.throwUserNotFound
import daniil.backend.mapper.UserMapper
import daniil.backend.repository.UserRepository
import daniil.backend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val userMapper: UserMapper
): UserService, UserDetailsService {



    override fun getUser(id: UUID, auth: Authentication): UserDto {
        val role = getRole(auth)
        if (UserRole.ADMIN != role) {
            throw UserHasNoPermissionException("user ${auth.name} doesn't have permission")
        }
        val user = userRepository.findByName(auth.name) ?: throwUserNotFound(auth.name)
        return userMapper.toDto(user)
    }

    override fun getUserByToken(auth: Authentication): UserDto {
        val user = userRepository.findByName(auth.name) ?: throwUserNotFound(auth.name)
        return userMapper.toDto(user)
    }

    override fun getUsers(req: GetUsersRequest, auth: Authentication): GetUsersResponse {
        val role = getRole(auth)
        if (UserRole.ADMIN != role) {
            throw UserHasNoPermissionException("user ${auth.name} doesn't have permission")
        }
        val pageRequest = PageRequest.of(req.page, req.size)
        val page = userRepository.findAll(pageRequest)
        return GetUsersResponse(
            page.stream()
                .sorted(compareBy(User::createdAt))
                .map { userMapper.toShortDto(it) }
                .toList(),
            page.totalPages,
            page.number,
            page.totalElements
        )
    }

    override fun blockUser(userId: UUID, auth: Authentication) {
        val role = getRole(auth)
        if (UserRole.ADMIN != role) {
            throw UserHasNoPermissionException("user ${auth.name} doesn't have permission")
        }
        val user = userRepository.findById(userId).orElseThrow { throwUserNotFound(userId) }
        user.isBlocked = !user.isBlocked
        userRepository.save(user)
    }

    override fun updateRole(req: UpdateRoleRequest, auth: Authentication) {
        val role = getRole(auth)
        if (UserRole.ADMIN != role) {
            throw UserHasNoPermissionException("user ${auth.name} doesn't have permission")
        }
        val user = userRepository.findById(req.userId).orElseThrow { throwUserNotFound(req.userId) }
        user.role = req.role
        userRepository.save(user)
    }

    override fun editUser(req: EditUserRequest, auth: Authentication) {
        val user = userRepository.findByName(auth.name) ?: throwUserNotFound(auth.name)
        if (user.id != req.userId) {
            throw UserHasNoPermissionException("user ${auth.name} doesn't have permission to edit other users!")
        }

        if (user.name != req.name && userRepository.existsByName(req.name)) {
            throw UserAlreadyExistsException("user with ${req.name} already exists")
        }

        if (user.mail != req.mail && userRepository.existsByMail(req.mail)) {
            throw UserAlreadyExistsException("user with ${req.mail} already exists")
        }

        user.name = req.name
        user.mail = req.mail

        userRepository.save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByName(username) ?: throwUserNotFound(username)
    }

}