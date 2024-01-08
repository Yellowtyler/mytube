package daniil.backend.mapper

import daniil.backend.dto.like.LikeDto
import daniil.backend.entity.Like
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface LikeMapper {
    fun toDto(entity: Like): LikeDto
}