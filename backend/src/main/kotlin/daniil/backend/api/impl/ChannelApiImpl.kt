package daniil.backend.api.impl

import daniil.backend.api.ChannelApi
import daniil.backend.dto.channel.*
import daniil.backend.service.ChannelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/channel")
class ChannelApiImpl(
    @Autowired private val channelService: ChannelService
): ChannelApi {

    @GetMapping("{id}")
    override fun getChannel(@PathVariable id: UUID, auth: Authentication?): ResponseEntity<ChannelDto> {
        return ResponseEntity.ok(channelService.getChannel(id, auth))
    }


    @GetMapping("/all")
    override fun getChannels(@RequestBody req: GetChannelsRequest, auth: Authentication?): ResponseEntity<GetChannelsResponse> {
        return ResponseEntity.ok(channelService.getChannels(req, auth))
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/subscribe")
    override fun subscribe(@RequestBody req: SubscribeChannelRequest, auth: Authentication) {
        channelService.subscribe(req, auth)
    }

    @PutMapping
    override fun editChannel(@RequestBody req: EditChannelRequest, auth: Authentication): ResponseEntity<ChannelDto> {
        return ResponseEntity.ok(channelService.editChannel(req, auth))
    }

    @PostMapping("/block")
    override fun blockChannel(@RequestBody req: BlockChannelRequest, auth: Authentication): ResponseEntity<ChannelDto> {
        return ResponseEntity.ok(channelService.blockChannel(req, auth))
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    override fun deleteChannel(@PathVariable id: UUID, auth: Authentication) {
        channelService.deleteChannel(id, auth)
    }
}