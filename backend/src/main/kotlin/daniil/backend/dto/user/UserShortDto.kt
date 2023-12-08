package daniil.backend.dto.user

import daniil.backend.enums.UserRole
import java.util.UUID

data class UserShortDto(val id: UUID, val name: String, val role: UserRole, val isBlocked: Boolean)
