package daniil.backend.dto.channel

import java.util.*

data class ChannelInfoDto(
    val id: UUID,
    val name: String,
    var isOwner: Boolean,
    var isSubscribed: Boolean,
    var subscribersCount: Int,

)
