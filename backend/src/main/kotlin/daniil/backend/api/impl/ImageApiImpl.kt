package daniil.backend.api.impl

import daniil.backend.api.ImageApi
import daniil.backend.service.ChannelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("api/image")
class ImageApiImpl(
    @Autowired private val channelService: ChannelService
): ImageApi {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/upload")
    override fun uploadPhoto(@RequestParam("type") type: String,
                             @RequestParam("user") userId: UUID,
                             @RequestParam("image") file: MultipartFile,
                             auth: Authentication) {
        channelService.uploadPhoto(type, userId, file, auth)
    }

    @GetMapping
    override fun getImage(@RequestParam("type") type: String,
                          @RequestParam("user") userId: UUID,
                          auth: Authentication
    ): ByteArray {
        return channelService.getPhoto(type,userId, auth)
    }

}