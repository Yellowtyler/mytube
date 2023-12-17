package daniil.backend.dto.channel

import java.util.UUID

data class EditChannelRequest(val id: UUID, val name: String, val description: String)
