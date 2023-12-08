package daniil.backend.api.impl

import daniil.backend.api.AuthApi
import daniil.backend.dto.auth.*
import daniil.backend.service.AuthService
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/auth")
class AuthApiImpl(private val authService: AuthService) : AuthApi {

    @ApiResponses(
        value = [
            ApiResponse(
            responseCode = "200",
            description = "Register request was successfully sent"
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
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    override fun register(@RequestBody req: RegisterRequest) {
        authService.register(req)
    }

    @ApiResponses(
        value = [
            ApiResponse(
            responseCode = "201",
            description = "User was successfully registered"
            ),
            ApiResponse(responseCode = "404", description = "User with this id wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
            responseCode = "403",
            description = "User doesn't have permission to resource"
        )]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/confirm-registration/{token}")
    override fun confirmRegistration(@PathVariable token: String) {
        authService.confirmRegistration(token)
    }

    @ApiResponses(
        value = [
            ApiResponse(
            responseCode = "200",
            description = "User was successfully logged in"
            ),
            ApiResponse(responseCode = "404", description = "User with this name wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
            responseCode = "403",
            description = "User doesn't have permission to resource"
        )]
    )
    @PostMapping("/login")
    override fun login(@RequestBody req: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(authService.login(req))
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User was successfully logged out"
            ),
            ApiResponse(responseCode = "404", description = "User with this name wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
                responseCode = "403",
                description = "User doesn't have permission to resource"
            )]
    )
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    override fun logout(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String, auth: Authentication) {
        authService.logout(token, auth)
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Reset password request was successfully sent"
            ),
            ApiResponse(responseCode = "404", description = "User with this name wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
                responseCode = "403",
                description = "User doesn't have permission to resource"
            )]
    )
    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    override fun resetPassword(@RequestBody req: ResetPasswordRequest) {
        authService.resetPassword(req)
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Password was changed successfully"
            ),
            ApiResponse(responseCode = "404", description = "User with this name wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
                responseCode = "403",
                description = "User doesn't have permission to resource"
            )]
    )
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    override fun changePassword(@RequestBody req: ChangePasswordRequest, auth: Authentication) {
        authService.changePassword(req, auth)
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "reset password mail was successfully send"
            ),
            ApiResponse(responseCode = "404", description = "User with this name wasn't found"),
            ApiResponse(responseCode = "401", description = "User is unauthorized"),
            ApiResponse(
                responseCode = "403",
                description = "User doesn't have permission to resource"
            )]
    )
    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    override fun forgotPassword(@RequestBody req: ForgotPasswordRequest) {
        authService.forgotPassword(req)
    }
}