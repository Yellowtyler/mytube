package daniil.backend.api

import daniil.backend.dto.auth.LoginRequest
import daniil.backend.dto.auth.RegisterRequest
import org.springframework.http.ResponseEntity

interface AuthApi {
    fun register(req: RegisterRequest)
    fun login(req: LoginRequest): ResponseEntity<String>
    fun logout(token: String)
    fun resetPassword()
}