package daniil.backend.api.impl

import daniil.backend.api.UserApi
import daniil.backend.dto.user.*
import daniil.backend.service.UserService
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/user")
class UserApiImpl(
    @Autowired private val userService: UserService
): UserApi {

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "successfully received user"
            ),
            ApiResponse(
                responseCode = "404",
                description = "User with this id wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
                responseCode = "403",
                description = "User doesn't have permission to resource")
        ]
    )
    @GetMapping("/{id}")
    override fun getUser(@PathVariable id: UUID, auth: Authentication): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.getUser(id, auth))
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "successfully received user"
            ),
            ApiResponse(
                responseCode = "404",
                description = "User with this id wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
                responseCode = "403",
                description = "User doesn't have permission to resource")
        ]
    )
    @GetMapping("/token")
    override fun getUserByToken(auth: Authentication): ResponseEntity<UserShortDto> {
        return ResponseEntity.ok(userService.getUserByToken(auth))
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "successfully received users"
            ),
            ApiResponse(
                responseCode = "404",
                description = "User with this id wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
                responseCode = "403",
                description = "User doesn't have permission to resource")
        ]
    )
    @GetMapping("/all")
    override fun getUsers(req: GetUsersRequest, auth: Authentication): ResponseEntity<GetUsersResponse> {
        return ResponseEntity.ok(userService.getUsers(req, auth))
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "successfully blocked user"
            ),
            ApiResponse(
                responseCode = "404",
                description = "User with this id wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
                responseCode = "403",
                description = "User doesn't have permission to resource")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/block/{userId}")
    override fun blockUser(@PathVariable userId: UUID, auth: Authentication) {
        userService.blockUser(userId, auth)
    }

    override fun uploadProfilePhoto(req: UploadProfilePhotoRequest, auth: Authentication) {
        TODO("Not yet implemented")
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "successfully updated user role"
            ),
            ApiResponse(
                responseCode = "404",
                description = "User with this id wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
                responseCode = "403",
                description = "User doesn't have permission to resource")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/role")
    override fun updateRole(@RequestBody req: UpdateRoleRequest, auth: Authentication) {
        userService.updateRole(req, auth)
    }
}