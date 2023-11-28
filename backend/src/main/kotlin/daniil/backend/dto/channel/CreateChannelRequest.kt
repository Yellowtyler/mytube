package daniil.backend.dto.channel

import java.util.UUID

data class CreateChannelRequest(val name: String, val ownerId: UUID) {

}
