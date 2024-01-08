package daniil.backend.dto.video

import java.util.UUID

data class EditVideoRequest(val videoId: UUID, val name: String, val description: String, val isHidden: Boolean)
