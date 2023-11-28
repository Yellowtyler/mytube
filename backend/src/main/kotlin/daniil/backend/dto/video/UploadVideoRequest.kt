package daniil.backend.dto.video

import org.springframework.web.multipart.MultipartFile

data class UploadVideoRequest(val name: String, val content: MultipartFile) {

}
