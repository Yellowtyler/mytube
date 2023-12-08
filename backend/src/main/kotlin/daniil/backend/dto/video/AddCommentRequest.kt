package daniil.backend.dto.video

import java.util.UUID

data class AddCommentRequest(val videoId: UUID, val content: String)
