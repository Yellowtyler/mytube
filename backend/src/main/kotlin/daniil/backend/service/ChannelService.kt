package daniil.backend.service

import daniil.backend.dto.channel.*
import org.springframework.security.core.Authentication
import java.util.*

interface ChannelService {
    fun getChannel(id: UUID, auth: Authentication?): ChannelDto
    fun getChannels(req: GetChannelsRequest, auth: Authentication?): GetChannelsResponse
    fun subscribe(req: SubscribeChannelRequest, auth: Authentication)
    fun blockChannel(req: BlockChannelRequest, auth: Authentication): ChannelDto
    fun deleteChannel(id: UUID, auth: Authentication)
    fun editChannel(req: EditChannelRequest, auth: Authentication): ChannelDto

}
