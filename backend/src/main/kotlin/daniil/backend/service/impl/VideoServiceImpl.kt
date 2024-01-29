package daniil.backend.service.impl

import daniil.backend.dto.video.*
import daniil.backend.entity.Channel
import daniil.backend.entity.Video
import daniil.backend.enums.UserRole
import daniil.backend.exception.UserHasNoPermissionException
import daniil.backend.exception.VideoIsBlockedException
import daniil.backend.exception.VideoIsHiddenException
import daniil.backend.extension.POSTER_DIR
import daniil.backend.extension.VIDEOS_DIR
import daniil.backend.extension.throwUserNotFound
import daniil.backend.extension.throwVideoNotFound
import daniil.backend.mapper.VideoMapper
import daniil.backend.repository.ChannelRepository
import daniil.backend.repository.LikeRepository
import daniil.backend.repository.UserRepository
import daniil.backend.repository.VideoRepository
import daniil.backend.service.VideoService
import io.github.oshai.kotlinlogging.KotlinLogging
import net.bramp.ffmpeg.FFprobe
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.Java2DFrameConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.OffsetDateTime
import java.util.*
import javax.imageio.ImageIO
import kotlin.io.path.exists
import kotlin.io.path.pathString


@Service
class VideoServiceImpl(
    @Autowired private val videoRepository: VideoRepository,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val channelRepository: ChannelRepository,
    @Autowired private val likeRepository: LikeRepository,
    @Autowired private val videoMapper: VideoMapper
): VideoService {

    private val logger = KotlinLogging.logger {  }

    override fun uploadVideo(name: String, poster: String, video: MultipartFile, authentication: Authentication): UploadVideoResponse {
        val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
        val channel = user.ownChannel!!

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

        val duration = getDuration(videoPath)
        val videoPoster = poster.ifEmpty {
            getFirstFrame(videoPath)
        }

        val newVideo = Video(
            null,
            name,
            0,
            isBlocked = false,
            isHidden = false,
            videoPath = videoName,
            description = "video description",
            createdAt = OffsetDateTime.now(),
            channel = channel,
            posterPath = videoPoster,
            duration = duration
        )

        val savedVideo = videoRepository.save(newVideo)

        val channelVideos = channel.videos
        channelVideos.add(savedVideo)
        channelRepository.save(channel)

        return UploadVideoResponse(savedVideo.id!!, name, videoName)
    }

    private fun getFirstFrame(videoPath: Path): String {
        val g = FFmpegFrameGrabber(videoPath.toFile())
        g.start()
        val converter = Java2DFrameConverter()
        var posterName = ""
        for (i in 0..50) {
            if (i == 50) {
                val frame = g.grabImage()
                val bi = converter.convert(frame)
                posterName = videoPath.fileName.toString() + UUID.randomUUID().toString() + ".png"
                val posterPath = POSTER_DIR + "/" +  posterName
                ImageIO.write(bi, "png", File(posterPath))
            }
        }

        g.stop()
        return posterName
    }


    private fun getDuration(videoPath: Path): Long {
        val ffprobe = FFprobe()
        val probeResult = ffprobe.probe(videoPath.pathString)

        val format = probeResult.getFormat()
        return format.duration.toLong()
    }

    override fun getVideoInfo(videoId: UUID, authentication: Authentication?): VideoDto {
        val video = videoRepository.findById(videoId)
           .orElseThrow {
                throwVideoNotFound(videoId)
           }

        if (video.isHidden && authentication == null) {
            logger.error { "getVideoInfo() - video $videoId is hidden" }
            throw VideoIsHiddenException("video ${video.name} is hidden")
        }

        if (video.isBlocked) {
            logger.error { "getVideoInfo() - video $videoId has been blocked" }
            throw VideoIsBlockedException("video ${video.name} has been blocked")
        }

        var isLike: Boolean? = null
        if (authentication != null) {
            val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
            if (video.isHidden && user.ownChannel != video.channel) {
               throw VideoIsHiddenException("video ${video.name} is hidden")
            }
            val like = likeRepository.findByUser_IdAndVideo_Id(user.id!!, videoId)
            isLike = like?.isLike
        }

        var likesCount = 0
        var dislikesCount = 0
        video.likes.forEach {
            if (it.isLike) {
                likesCount++
            } else {
                dislikesCount++
            }
        }


        val videoDto = videoMapper.toDto(video)
        videoDto.isLike = isLike
        videoDto.dislikesCount = dislikesCount
        videoDto.likesCount = likesCount
        return videoDto
    }

    override fun getVideo(videoId: UUID, authentication: Authentication?): ByteArray {
        val video = videoRepository.findById(videoId).orElseThrow { throwVideoNotFound(videoId) }

        if (video.isHidden) {
            if (authentication != null) {
                val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
                if (user.ownChannel != video.channel) {
                    logger.error { "getVideo() - video $videoId is hidden" }
                    throw VideoIsHiddenException("video ${video.name} is hidden")
                }
            } else {
                logger.error { "getVideo() - video $videoId is hidden" }
                throw VideoIsHiddenException("video ${video.name} is hidden")
            }
       }

        if (video.isBlocked) {
            logger.error { "getVideo() - video $videoId has been blocked" }
            throw VideoIsBlockedException("video ${video.name} has been blocked")
        }

        val dirPath = Path.of(VIDEOS_DIR)
        if (!Files.exists(dirPath))
            Files.createDirectory(dirPath)

        return try {
            val fileNamePath = Paths.get(VIDEOS_DIR, video.videoPath)
            Files.readAllBytes(fileNamePath)
        } catch (e: NoSuchFileException) {
            logger.warn { "getVideo() - video $videoId doesn't exist" }
            ByteArray(0)
        }
    }

    override fun getVideos(req: GetVideosRequest, authentication: Authentication?): GetVideosResponse {
        val spec = createSpec(req)
        val pageRequest = PageRequest.of(req.page, req.size)
        val videosPage = videoRepository.findAll(spec, pageRequest)

        val videos =
            if (authentication == null) {
                videosPage.filter { !it.isHidden }.toMutableList()
            } else {
                val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
                val userChannel = user.ownChannel
                val videosIds = videosPage.map { it.id!! }.toList()
                val videosChannels = channelRepository.findAllByVideosId(videosIds)
                if (!videosChannels.all { it == userChannel }) {
                    videosPage.filter { !it.isHidden }.toMutableList()
                }
                videosPage.toMutableList()
            }
            .filter { !it.isBlocked }
            .map {
                videoMapper.toShortDto(it)
            }
            .sortedBy {
                it.createdAt
            }
            .toList()

        return GetVideosResponse(videos, videosPage.totalPages, videosPage.number, videosPage.totalElements)
    }

    override fun editVideo(req: EditVideoRequest, authentication: Authentication): VideoDto {
        val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
        val video = videoRepository.findById(req.videoId).orElseThrow { throwVideoNotFound(req.videoId) }
        val channelOfVideo = channelRepository.findByVideos_Id(video.id)
        if (user.ownChannel != channelOfVideo) {
            throw UserHasNoPermissionException("${user.name} doesn't have permission to edit this video")
        }
        video.name = req.name
        video.description = req.description
        video.isHidden = req.isHidden
        return videoMapper.toDto(videoRepository.save(video))
    }

    override fun blockVideo(id: UUID, authentication: Authentication): VideoDto {
        val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
        if (user.role != UserRole.MODERATOR) {
            throw UserHasNoPermissionException("user ${authentication.name} doesn't have permission to block video")
        }

        val video = videoRepository.findById(id).orElseThrow { throwVideoNotFound(id) }
        video.isBlocked = !video.isBlocked
        return videoMapper.toDto(videoRepository.save(video))
    }

    override fun deleteVideo(id: UUID, authentication: Authentication) {
        val user = userRepository.findByName(authentication.name) ?: throwUserNotFound(authentication.name)
        if (user.role != UserRole.MODERATOR) {
            throw UserHasNoPermissionException("user ${authentication.name} doesn't have permission to delete video")
        }

        val video = videoRepository.findById(id).orElseThrow { throwVideoNotFound(id) }

        if (!video.isBlocked) {
            throw Exception("video $id must be blocked before deletion")
        }

        videoRepository.delete(video)

        val path = Path.of(VIDEOS_DIR, video.videoPath)
        Files.delete(path)
    }


    override fun incrementViews(videoId: UUID) {
        val video = videoRepository.findById(videoId).orElseThrow { throwVideoNotFound(videoId) }
        video.views++
        videoRepository.save(video)
    }

    private fun createSpec(req: GetVideosRequest): Specification<Video?> {
        if (req.name != null) {
            var spec = Specification<Video?> {
                    video,
                    _,
                    criteriaBuilder -> criteriaBuilder.like(video.get("name"), req.name)
            }

            if (req.channelId != null) {
                spec = spec.and {
                        video,
                        _,
                        criteriaBuilder ->
                    criteriaBuilder.equal(video.join<Video, Channel>("channel").get<UUID>("id"), req.channelId)
                }
            }

            return spec
        } else {
            return Specification<Video?> {
                    video,
                    _,
                    criteriaBuilder ->
                criteriaBuilder.equal(video.join<Video, Channel>("channel").get<UUID>("id"), req.channelId)
            }
        }
    }

}
