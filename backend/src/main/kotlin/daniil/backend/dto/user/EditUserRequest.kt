package daniil.backend.dto.user

import java.util.UUID

data class EditUserRequest(val userId: UUID, val name: String, val mail: String)
