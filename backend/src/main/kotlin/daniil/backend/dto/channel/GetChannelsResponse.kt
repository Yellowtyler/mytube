package daniil.backend.dto.channel

data class GetChannelsResponse(val list: List<ChannelDto>, val totalPages: Int, val currentPage: Int, val totalItems: Long)
