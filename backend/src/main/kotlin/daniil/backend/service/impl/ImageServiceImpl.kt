package daniil.backend.service.impl

import daniil.backend.dto.image.UploadResponseDto
import daniil.backend.enums.PhotoType
import daniil.backend.exception.UserHasNoPermissionException
import daniil.backend.exception.VideoNotFoundException
import daniil.backend.extension.*
import daniil.backend.repository.UserRepository
import daniil.backend.repository.VideoRepository
import daniil.backend.service.ImageService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class ImageServiceImpl(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val videoRepository: VideoRepository
): ImageService {

    private val logger = KotlinLogging.logger {  }

    override fun uploadPhoto(type: PhotoType, userId: UUID, file: MultipartFile, auth: Authentication): UploadResponseDto {
        val user = userRepository.findById(userId).orElseThrow { throwUserNotFound(userId) }
        if (user.id != userId) {
            throw UserHasNoPermissionException("user ${auth.name} can't edit other users' images")
        }
        val dir = when (type) {
            PhotoType.PROFILE -> PROFILE_PHOTOS_DIR
            PhotoType.BACKGROUND -> BACKGROUND_DIR
            PhotoType.POSTER -> POSTER_DIR
        }
        val dirPath = Path.of(dir)
        if (!Files.exists(dirPath))
            Files.createDirectory(dirPath)

        val splittedFileName = file.originalFilename!!.split(".")
        if (splittedFileName.size != 2) {
            throw Exception("file doesn't have type")
        }
        val fileName = splittedFileName[0] + UUID.randomUUID() + "." + splittedFileName[1]
        val filePath = Paths.get(dir, fileName)
        Files.write(filePath, file.bytes)

        val channel = user.ownChannel!!

        when (type) {
            PhotoType.BACKGROUND -> channel.backgroundPhoto = fileName
            PhotoType.PROFILE -> channel.profilePhoto = fileName
            PhotoType.POSTER -> ""
        }

        return UploadResponseDto(fileName)
    }

    override fun getPhoto(type: PhotoType, userId: UUID?, videoId: UUID?): ByteArray {
        val fileName =  when (type) {
            PhotoType.PROFILE -> {
                if (userId == null) {
                    throw VideoNotFoundException("query param 'user' is absent")
                }
                val user = userRepository.findById(userId).orElseThrow { throwUserNotFound(userId) }
                user.ownChannel!!.profilePhoto
            }
            PhotoType.BACKGROUND -> {
                if (userId == null) {
                    throw VideoNotFoundException("query param 'user' is absent")
                }
                val user = userRepository.findById(userId).orElseThrow { throwUserNotFound(userId) }
                user.ownChannel!!.backgroundPhoto
            }
            PhotoType.POSTER -> {
                if (videoId == null) {
                    throw VideoNotFoundException("query param 'video' is absent")
                } else {
                    videoRepository.findById(videoId).orElseThrow { throwVideoNotFound(videoId) }.posterPath
                }
            }
        }

        val dir = when (type) {
            PhotoType.PROFILE -> PROFILE_PHOTOS_DIR
            PhotoType.BACKGROUND -> BACKGROUND_DIR
            PhotoType.POSTER -> POSTER_DIR
        }

        val dirPath = Path.of(dir)
        if (!Files.exists(dirPath))
            Files.createDirectory(dirPath)

        return try {
            val fileNamePath = Paths.get(dir, fileName)
            Files.readAllBytes(fileNamePath)
        } catch (e: NoSuchFileException) {
            logger.warn { "getPhoto() - $fileName doesn't exist" }
            ByteArray(0)
        }
    }
}