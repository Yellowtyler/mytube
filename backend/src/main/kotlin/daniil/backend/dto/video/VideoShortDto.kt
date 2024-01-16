package daniil.backend.dto.video

import java.time.OffsetDateTime
import java.util.*

data class VideoShortDto(
    val id: UUID,

    val name: String,

    val views: Long,

    val isBlocked: Boolean,

    val isHidden: Boolean,

    val createdAt: OffsetDateTime,

    val duration: Long
)
