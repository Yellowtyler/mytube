package daniil.backend.service.impl

import daniil.backend.dto.like.LikeDto
import daniil.backend.dto.video.LikeVideoRequest
import daniil.backend.entity.Like
import daniil.backend.exception.LikeNotFoundException
import daniil.backend.extension.throwUserNotFound
import daniil.backend.extension.throwVideoNotFound
import daniil.backend.mapper.LikeMapper
import daniil.backend.repository.LikeRepository
import daniil.backend.repository.UserRepository
import daniil.backend.repository.VideoRepository
import daniil.backend.service.LikeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class LikeServiceImpl(
    @Autowired private val videoRepository: VideoRepository,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val likeRepository: LikeRepository,
    @Autowired private val likeMapper: LikeMapper
): LikeService{
    override fun likeVideo(req: LikeVideoRequest, authentication: Authentication): LikeDto {
        val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
        val video = videoRepository.findById(req.videoId).orElseThrow { throwVideoNotFound(req.videoId) }
        var like = likeRepository.findByUser_IdAndVideo_Id(user.id!!, video.id!!)
        if (like == null) {
            like = Like(
                user = user,
                video = video,
                createdAt = OffsetDateTime.now(),
                isLike = req.isLike
            )
        }

        like.isLike = req.isLike
        return likeMapper.toDto(likeRepository.save(like))
    }
    override fun deleteLike(videoId: UUID, authentication: Authentication) {
        val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
        val video = videoRepository.findById(videoId).orElseThrow { throwVideoNotFound(videoId) }
        val like = likeRepository.findByUser_IdAndVideo_Id(user.id!!, video.id!!) ?: throw LikeNotFoundException("like for video=$videoId and user=${user.name} not found")
        likeRepository.delete(like)
    }
}