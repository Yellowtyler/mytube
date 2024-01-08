package daniil.backend.api

import daniil.backend.dto.like.LikeDto
import daniil.backend.dto.video.LikeVideoRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import java.util.UUID

interface LikeApi {
    fun likeVideo(req: LikeVideoRequest, authentication: Authentication): ResponseEntity<LikeDto>
    fun deleteLike(videoId: UUID, authentication: Authentication)
}