package daniil.backend.dto.channel

import java.util.*

data class VideoOwnerDto(
    val id: UUID,
    val name: String,
    var subscribersCount: Int,
)
