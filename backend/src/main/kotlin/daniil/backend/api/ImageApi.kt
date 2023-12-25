package daniil.backend.api

import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile

interface ImageApi {
    fun uploadPhoto(type: String, file: MultipartFile, auth: Authentication)
    fun getImage(type: String, auth: Authentication): ByteArray
}