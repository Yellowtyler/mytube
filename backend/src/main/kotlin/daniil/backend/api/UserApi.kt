package daniil.backend.api

import daniil.backend.dto.user.GetUsersRequest
import daniil.backend.dto.user.UpdateRoleRequest
import daniil.backend.dto.user.UserDto
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import java.util.UUID

interface UserApi {
    fun getUser(id: UUID, auth: Authentication): ResponseEntity<UserDto>
    fun getUsers(req: GetUsersRequest, auth: Authentication): ResponseEntity<List<UserDto>>
    fun blockUser(userId: UUID, auth: Authentication)
    fun updateRole(req: UpdateRoleRequest, auth: Authentication)
}