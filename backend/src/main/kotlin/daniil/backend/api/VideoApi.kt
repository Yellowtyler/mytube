package daniil.backend.api

import daniil.backend.dto.video.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface VideoApi {
    fun uploadVideo(channelId: UUID, name: String, video: MultipartFile): ResponseEntity<UploadVideoResponse>
    fun getVideo(id: UUID): ResponseEntity<VideoDto>
    fun getVideos(req: GetVideosRequest, authentication: Authentication): ResponseEntity<List<VideoDto>>
    fun editVideo(req: EditVideoRequest, authentication: Authentication)
    fun blockVideo(id: UUID, authentication: Authentication)
    fun deleteVideo(id: UUID, authentication: Authentication)
    fun likeVideo(req: LikeVideoRequest, authentication: Authentication)
    fun incrementViews(authentication: Authentication)
}
