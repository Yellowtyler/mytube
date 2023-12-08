package daniil.backend.dto.channel

import java.util.UUID

data class CreateChannelResponse(val id: UUID, val name: String, val ownerId: UUID)
