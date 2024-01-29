package daniil.backend.api

import daniil.backend.dto.video.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface VideoApi {
    fun uploadVideo(name: String, poster: String, video: MultipartFile, authentication: Authentication)
    fun getVideoInfo(id: UUID, authentication: Authentication?): ResponseEntity<VideoDto>
    fun getVideo(videoId: UUID, authentication: Authentication?): ByteArray
    fun getVideos(req: GetVideosRequest, authentication: Authentication?): ResponseEntity<GetVideosResponse>
    fun editVideo(req: EditVideoRequest, authentication: Authentication): ResponseEntity<VideoDto>
    fun blockVideo(id: UUID, authentication: Authentication): ResponseEntity<VideoDto>
    fun deleteVideo(id: UUID, authentication: Authentication)
    fun incrementViews(videoId: UUID)
}
