package daniil.backend.api

import daniil.backend.dto.auth.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface AuthApi {
    fun register(req: RegisterRequest)
    fun confirmRegistration(token: String)
    fun login(req: LoginRequest): ResponseEntity<LoginResponse>
    fun logout(token: String, auth: Authentication)
    fun resetPassword(req: ResetPasswordRequest)
    fun changePassword(req: ChangePasswordRequest, auth: Authentication)
    fun forgotPassword(req: ForgotPasswordRequest)
}