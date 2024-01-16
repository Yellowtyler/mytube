package daniil.backend.api.impl

import daniil.backend.api.ImageApi
import daniil.backend.enums.PhotoType
import daniil.backend.service.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("api/image")
class ImageApiImpl(
    @Autowired private val imageService: ImageService
): ImageApi {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/upload")
    override fun uploadPhoto(@RequestParam("type") type: String,
                             @RequestParam("user") userId: UUID,
                             @RequestParam("image") file: MultipartFile,
                             auth: Authentication) {
        imageService.uploadPhoto(mapType(type), userId, file, auth)
    }

    @GetMapping
    override fun getImage(@RequestParam("type") type: String,
                          @RequestParam("user") userId: UUID,
                          @RequestParam(name = "video", required = false) videoId: UUID?
    ): ByteArray {
        return imageService.getPhoto(mapType(type), userId, videoId)
    }

    private fun mapType(type: String): PhotoType {
        return when (type) {
            "profile" -> PhotoType.PROFILE
            "background" -> PhotoType.BACKGROUND
            "poster" -> PhotoType.POSTER
            else -> throw Exception("this photo type doesn't exist")
        }
    }

}