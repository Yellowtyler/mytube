package daniil.backend.service

import daniil.backend.dto.video.*
import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface VideoService {
    fun uploadVideo(channelId: UUID, name: String, video: MultipartFile, authentication: Authentication): UploadVideoResponse
    fun getVideo(id: UUID): VideoDto
    fun getVideos(req: GetVideosRequest, authentication: Authentication): List<VideoDto>
    fun editVideo(req: EditVideoRequest, authentication: Authentication)
    fun blockVideo(id: UUID, authentication: Authentication)
    fun deleteVideo(id: UUID, authentication: Authentication)
    fun likeVideo(req: LikeVideoRequest, authentication: Authentication)
    fun incrementViews(authentication: Authentication)
}