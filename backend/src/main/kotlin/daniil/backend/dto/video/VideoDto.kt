package daniil.backend.dto.video

import daniil.backend.dto.comment.CommentDto
import java.util.*

data class VideoDto(
    val id: UUID,

    val name: String,

    val views: Long,

    val isBlocked: Boolean,

    val isHidden: Boolean,

    val videoPath: String,

    val comments: List<CommentDto>,

    val likesCount: Int,

    val isLike: Boolean?,

    val dislikesCount: Int

){

}
