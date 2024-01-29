package daniil.backend.service

import daniil.backend.dto.video.*
import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface VideoService {
    fun uploadVideo(name: String, poster: String, video: MultipartFile, authentication: Authentication): UploadVideoResponse
    fun getVideoInfo(videoId: UUID, authentication: Authentication?): VideoDto
    fun getVideo(videoId: UUID, authentication: Authentication?): ByteArray
    fun getVideos(req: GetVideosRequest, authentication: Authentication?): GetVideosResponse
    fun editVideo(req: EditVideoRequest, authentication: Authentication): VideoDto
    fun blockVideo(id: UUID, authentication: Authentication): VideoDto
    fun deleteVideo(id: UUID, authentication: Authentication)
    fun incrementViews(videoId: UUID)
}