package daniil.backend.service.impl

import daniil.backend.dto.video.*
import daniil.backend.entity.Video
import daniil.backend.exception.UserHasNoPermissionException
import daniil.backend.extension.VIDEOS_DIR
import daniil.backend.extension.throwChannelNotFound
import daniil.backend.extension.throwUserNotFound
import daniil.backend.repository.ChannelRepository
import daniil.backend.repository.UserRepository
import daniil.backend.repository.VideoRepository
import daniil.backend.service.VideoService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.exists

@Service
class VideoServiceImpl(
    @Autowired private val videoRepository: VideoRepository,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val channelRepository: ChannelRepository
): VideoService {

    private val logger = KotlinLogging.logger {  }

    override fun uploadVideo(channelId: UUID, name: String, video: MultipartFile, authentication: Authentication): UploadVideoResponse {
        val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
        val channel = channelRepository.findById(channelId).orElseThrow {
            throwChannelNotFound(channelId)
        }

        if (user != channel.owner) {
            throw UserHasNoPermissionException("user ${authentication.name} doesn't have permission")
        }

        val dir = Path.of(VIDEOS_DIR)

        if (!dir.exists())
            Files.createDirectory(dir)

        val splitedVideoName = video.originalFilename!!.split(".")
        val videoName = splitedVideoName[0] + UUID.randomUUID() + "." + splitedVideoName[1]
        val videoPath = Paths.get(VIDEOS_DIR, videoName)
        Files.write(videoPath, video.bytes)

        val newVideo = Video(
            null,
            name,
            0,
            isBlocked = false,
            isHidden = false,
            videoPath = videoName,
            description = "video description"
        )

        val savedVideo = videoRepository.save(newVideo)

        val channelVideos = channel.videos
        channelVideos.add(savedVideo)
        channelRepository.save(channel)

        return UploadVideoResponse(savedVideo.id!!, name, videoName)
    }

    override fun getVideo(id: UUID): VideoDto {
        TODO("Not yet implemented")
    }

    override fun getVideos(req: GetVideosRequest, authentication: Authentication): List<VideoDto> {
        TODO("Not yet implemented")
    }

    override fun editVideo(req: EditVideoRequest, authentication: Authentication) {
        TODO("Not yet implemented")
    }

    override fun blockVideo(id: UUID, authentication: Authentication) {
        TODO("Not yet implemented")
    }

    override fun deleteVideo(id: UUID, authentication: Authentication) {
        TODO("Not yet implemented")
    }

    override fun likeVideo(req: LikeVideoRequest, authentication: Authentication) {
        TODO("Not yet implemented")
    }

    override fun incrementViews(authentication: Authentication) {
        TODO("Not yet implemented")
    }
}