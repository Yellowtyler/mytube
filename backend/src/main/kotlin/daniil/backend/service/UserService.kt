package daniil.backend.service

import daniil.backend.dto.user.*
import org.springframework.security.core.Authentication
import java.util.*

interface UserService {
    fun getUser(id: UUID, auth: Authentication): UserDto
    fun getUserByToken(auth: Authentication): UserDto
    fun getUsers(req: GetUsersRequest, auth: Authentication): GetUsersResponse
    fun blockUser(userId: UUID, auth: Authentication): UserDto
    fun updateRole(req: UpdateRoleRequest, auth: Authentication): UserDto
    fun editUser(req: EditUserRequest, auth: Authentication): UserDto
}