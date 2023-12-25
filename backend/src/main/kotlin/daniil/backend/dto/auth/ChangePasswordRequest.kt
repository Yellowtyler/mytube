package daniil.backend.dto.auth

data class ChangePasswordRequest(val oldPassword: String, val newPassword: String)
