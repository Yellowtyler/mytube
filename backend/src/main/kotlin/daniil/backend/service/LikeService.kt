package daniil.backend.service

import daniil.backend.dto.like.LikeDto
import daniil.backend.dto.video.LikeVideoRequest
import org.springframework.security.core.Authentication
import java.util.UUID

interface LikeService {
    fun likeVideo(req: LikeVideoRequest, authentication: Authentication): LikeDto
    fun deleteLike(videoId: UUID, authentication: Authentication)
}