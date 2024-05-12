package daniil.backend.api.impl

import daniil.backend.api.LikeApi
import daniil.backend.dto.like.LikeDto
import daniil.backend.dto.video.LikeVideoRequest
import daniil.backend.service.LikeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/like")
class LikeApiImpl(
    @Autowired private val likeService: LikeService
): LikeApi {

    @PostMapping
    override fun likeVideo(@RequestBody req: LikeVideoRequest, authentication: Authentication): ResponseEntity<LikeDto> {
        return ResponseEntity.ok(likeService.likeVideo(req, authentication))
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{videoId}")
    override fun deleteLike(@PathVariable videoId: UUID, authentication: Authentication) {
        likeService.deleteLike(videoId, authentication)
    }
}