package daniil.backend.mapper

import daniil.backend.dto.channel.ChannelDto
import daniil.backend.dto.channel.ChannelShortDto
import daniil.backend.entity.Channel
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface ChannelMapper {
    fun toDto(entity: Channel): ChannelDto

    @Mapping(source = "blocked", target = "isBlocked")
    fun toShortDto(entity: Channel): ChannelShortDto
}