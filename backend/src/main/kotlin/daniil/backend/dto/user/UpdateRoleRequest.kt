package daniil.backend.dto.user

import daniil.backend.enums.UserRole
import java.util.UUID

data class UpdateRoleRequest(val userId: UUID, val role: UserRole)
