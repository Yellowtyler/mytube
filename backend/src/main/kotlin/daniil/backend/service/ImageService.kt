package daniil.backend.service

import daniil.backend.dto.image.UploadResponseDto
import daniil.backend.enums.PhotoType
import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface ImageService {
    fun uploadPhoto(type: PhotoType, userId: UUID, file: MultipartFile, auth: Authentication): UploadResponseDto
    fun getPhoto(type: PhotoType, userId: UUID, videoId: UUID?): ByteArray
}