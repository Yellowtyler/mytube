package daniil.backend.dto.comment

import java.time.OffsetDateTime
import java.util.*

data class CommentDto(
    val id: UUID,

    val content: String,

    val createdAt: OffsetDateTime
)
