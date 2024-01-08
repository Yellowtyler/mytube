package daniil.backend.mapper

import daniil.backend.dto.video.VideoDto
import daniil.backend.dto.video.VideoShortDto
import daniil.backend.entity.Video
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface VideoMapper {

    fun toDto(entity: Video): VideoDto

    @Mapping(source = "blocked", target = "isBlocked")
    @Mapping(source = "hidden", target = "isHidden")
    fun toShortDto(entity: Video): VideoShortDto
}