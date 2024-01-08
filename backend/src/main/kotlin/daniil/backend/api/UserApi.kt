package daniil.backend.api

import daniil.backend.dto.user.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import java.util.*

interface UserApi {
    fun getUser(id: UUID, auth: Authentication): ResponseEntity<UserDto>
    fun getUserByToken(header: String, auth: Authentication): ResponseEntity<UserDto>
    fun getUsers(req: GetUsersRequest, auth: Authentication): ResponseEntity<GetUsersResponse>
    fun blockUser(userId: UUID, auth: Authentication): ResponseEntity<UserDto>
    fun editUser(req: EditUserRequest, auth: Authentication): ResponseEntity<UserDto>
    fun updateRole(req: UpdateRoleRequest, auth: Authentication): ResponseEntity<UserDto>
}