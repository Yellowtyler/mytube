package daniil.backend.api

import daniil.backend.dto.channel.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import java.util.UUID

interface ChannelApi {
    fun getChannel(auth: Authentication): ResponseEntity<ChannelDto>
    fun getChannels(req: GetChannelsRequest, auth: Authentication): ResponseEntity<GetChannelsResponse>
    fun subscribe(req: SubscribeChannelRequest, auth: Authentication)
    fun editChannel(req: EditChannelRequest, auth: Authentication)
    fun blockChannel(req: BlockChannelRequest, auth: Authentication)
    fun deleteChannel(id: UUID, auth: Authentication)
}