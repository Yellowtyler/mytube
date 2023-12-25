package daniil.backend.api.impl

import daniil.backend.api.ImageApi
import daniil.backend.service.ChannelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/image")
class ImageApiImpl(
    @Autowired private val channelService: ChannelService
): ImageApi {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/upload/{type}")
    override fun uploadPhoto(@PathVariable type: String, @RequestParam("image") file: MultipartFile, auth: Authentication) {
        channelService.uploadPhoto(type, file, auth)
    }

    @GetMapping("{type}")
    override fun getImage(@PathVariable type: String, auth: Authentication): ByteArray {
        return channelService.getPhoto(type, auth)
    }

}