package daniil.backend.dto.auth

data class RegisterRequest(val name: String, val mail: String, val password: String)
