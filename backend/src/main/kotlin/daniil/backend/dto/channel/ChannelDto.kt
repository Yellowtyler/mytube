package daniil.backend.dto.channel

import daniil.backend.dto.user.UserDto
import java.time.OffsetDateTime
import java.util.*

data class ChannelDto(
    val id: UUID,
    val name: String,
    val description: String,
    val createdAt: OffsetDateTime,
    var isBlocked: Boolean,
    val owner: UserDto,
    var subscribersCount: Int,
    var videosCount: Int,
    var isSubscribed: Boolean
)
