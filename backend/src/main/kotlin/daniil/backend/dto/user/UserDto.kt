package daniil.backend.dto.user

import daniil.backend.enums.UserRole
import java.time.OffsetDateTime
import java.util.*

data class UserDto (
    val id: UUID,
    val name: String,
    val mail: String,
    val createdAt: OffsetDateTime,
    val userRole: UserRole,
    val isBlocked: Boolean
){

}
