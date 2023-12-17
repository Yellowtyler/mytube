package daniil.backend.dto.user

import org.springframework.web.multipart.MultipartFile

data class UploadProfilePhotoRequest(val img: MultipartFile)
