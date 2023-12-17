package daniil.backend.dto.channel

data class GetChannelsRequest(val name: String, val page: Int, val size: Int)
