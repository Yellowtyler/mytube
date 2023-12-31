package daniil.backend.mapper

import daniil.backend.dto.channel.ChannelDto
import daniil.backend.dto.channel.ChannelShortDto
import daniil.backend.entity.Channel
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ChannelMapper {
    fun toDto(entity: Channel): ChannelDto
    fun toShortDto(entity: Channel): ChannelShortDto
}