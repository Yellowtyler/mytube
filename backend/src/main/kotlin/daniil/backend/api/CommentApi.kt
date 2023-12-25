package daniil.backend.api

import daniil.backend.dto.video.AddCommentRequest
import daniil.backend.dto.video.AddCommentResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import java.util.*

interface CommentApi {
    fun addComment(req: AddCommentRequest, authentication: Authentication): ResponseEntity<AddCommentResponse>
    fun deleteComment(commentId: UUID, authentication: Authentication)
}