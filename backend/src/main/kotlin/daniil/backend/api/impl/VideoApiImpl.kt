package daniil.backend.api.impl

import daniil.backend.api.VideoApi
import daniil.backend.dto.video.*
import daniil.backend.service.VideoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("api/video")
class VideoApiImpl(
   @Autowired private val videoService: VideoService
): VideoApi {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    override fun uploadVideo(
        @RequestParam("name") name: String,
        @RequestParam("poster") poster: String,
        @RequestParam("video") file: MultipartFile,
        authentication: Authentication
    ) {
        videoService.uploadVideo(name, poster, file, authentication)
    }

    @GetMapping("/info/{id}")
    override fun getVideoInfo(@PathVariable id: UUID, authentication: Authentication?): ResponseEntity<VideoDto> {
        return ResponseEntity.ok(videoService.getVideoInfo(id, authentication))
    }

    @GetMapping("{videoId}")
    override fun getVideo(@PathVariable videoId: UUID, authentication: Authentication?): ByteArray {
        return videoService.getVideo(videoId, authentication)
    }

    @GetMapping("/all")
    override fun getVideos(req: GetVideosRequest, authentication: Authentication?): ResponseEntity<GetVideosResponse> {
        return ResponseEntity.ok(videoService.getVideos(req, authentication))
    }

    @PutMapping
    override fun editVideo(@RequestBody req: EditVideoRequest, authentication: Authentication): ResponseEntity<VideoDto> {
        return ResponseEntity.ok(videoService.editVideo(req, authentication))
    }

    @PostMapping("/block/{id}")
    override fun blockVideo(@PathVariable id: UUID, authentication: Authentication): ResponseEntity<VideoDto> {
        return ResponseEntity.ok(videoService.blockVideo(id, authentication))
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    override fun deleteVideo(@PathVariable id: UUID, authentication: Authentication) {
        videoService.deleteVideo(id, authentication)
    }

    @PatchMapping("/views")
    override fun incrementViews(videoId: UUID) {
        videoService.incrementViews(videoId)
    }
}