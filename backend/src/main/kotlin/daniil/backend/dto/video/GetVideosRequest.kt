package daniil.backend.dto.video

import java.util.UUID

data class GetVideosRequest(val channelId: UUID?, val name: String?, val page: Int = 0, val size: Int = 5)
