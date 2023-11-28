package daniil.backend.api

import daniil.backend.dto.channel.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import java.util.UUID

interface ChannelApi {
    fun createChannel(req: CreateChannelRequest, auth: Authentication): ResponseEntity<CreateChannelResponse>
    fun getChannel(id: UUID, auth: Authentication): ResponseEntity<ChannelDto>
    fun getChannels(req: GetChannelsRequest, auth: Authentication): ResponseEntity<List<ChannelDto>>
    fun subscribe(req: SubscribeChannelRequest, auth: Authentication)
    fun blockChannel(channelId: UUID, auth: Authentication)
    fun deleteChannel(id: UUID, auth: Authentication)
}