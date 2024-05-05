package daniil.backend.api

import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface ImageApi {
    fun uploadPhoto(type: String, userId: UUID, file: MultipartFile, auth: Authentication)
    fun getImage(type: String, userId: UUID?, channelId: UUID?, videoId: UUID?): ByteArray
}