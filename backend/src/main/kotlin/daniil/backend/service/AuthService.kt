package daniil.backend.service

import daniil.backend.dto.auth.*
import org.springframework.security.core.Authentication

interface AuthService {
    fun register(req: RegisterRequest)
    fun login(req: LoginRequest): LoginResponse
    fun logout(token: String, auth: Authentication)
    fun resetPassword(req: ResetPasswordRequest)
    fun confirmRegistration(token: String)
    fun changePassword(req: ChangePasswordRequest, auth: Authentication)
    fun forgotPassword(req: ForgotPasswordRequest)
}
