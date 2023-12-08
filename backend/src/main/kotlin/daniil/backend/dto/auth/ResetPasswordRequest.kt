package daniil.backend.dto.auth

data class ResetPasswordRequest(val token: String, val newPassword: String)
