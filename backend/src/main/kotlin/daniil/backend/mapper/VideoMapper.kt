package daniil.backend.mapper

import daniil.backend.dto.channel.VideoOwnerDto
import daniil.backend.dto.video.VideoDto
import daniil.backend.dto.video.VideoShortDto
import daniil.backend.entity.Channel
import daniil.backend.entity.Video
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named

@Mapper(componentModel = "spring")
interface VideoMapper {

    @Mapping(source = "channel", target = "channel", qualifiedByName = ["mapChannel"])
    fun toDto(entity: Video): VideoDto

    @Named("mapChannel")
    fun mapChannel(channel: Channel): VideoOwnerDto

    @Mapping(source = "blocked", target = "isBlocked")
    @Mapping(source = "hidden", target = "isHidden")
    fun toShortDto(entity: Video): VideoShortDto
}