package daniil.backend.dto.auth

data class ChangePasswordRequest(val name: String, val oldPassword: String, val newPassword: String)
