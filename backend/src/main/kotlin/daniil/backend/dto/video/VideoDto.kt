package daniil.backend.dto.video

import daniil.backend.dto.channel.VideoOwnerDto
import daniil.backend.dto.comment.CommentDto
import java.time.OffsetDateTime
import java.util.*

data class VideoDto(
    val id: UUID,

    val name: String,

    val views: Long,

    val isBlocked: Boolean,

    val isHidden: Boolean,

    val comments: List<CommentDto>,

    var likesCount: Int,

    var isLike: Boolean?,

    var dislikesCount: Int,

    val createdAt: OffsetDateTime,
    val channel: VideoOwnerDto
)
