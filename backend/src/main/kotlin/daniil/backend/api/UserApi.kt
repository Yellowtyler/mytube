package daniil.backend.api

import daniil.backend.dto.user.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import java.util.UUID

interface UserApi {
    fun getUser(id: UUID, auth: Authentication): ResponseEntity<UserDto>
    fun getUserByToken(auth: Authentication): ResponseEntity<UserShortDto>
    fun getUsers(req: GetUsersRequest, auth: Authentication): ResponseEntity<GetUsersResponse>
    fun blockUser(userId: UUID, auth: Authentication)
    fun uploadProfilePhoto(req: UploadProfilePhotoRequest, auth: Authentication)
    fun updateRole(req: UpdateRoleRequest, auth: Authentication)
}