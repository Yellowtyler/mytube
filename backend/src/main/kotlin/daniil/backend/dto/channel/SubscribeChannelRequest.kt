package daniil.backend.dto.channel

import java.util.UUID

data class SubscribeChannelRequest(val userId: UUID, val channelId: UUID) {

}
