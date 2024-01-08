package daniil.backend.mapper

import daniil.backend.dto.user.UserDto
import daniil.backend.dto.user.UserShortDto
import daniil.backend.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDto(entity: User): UserDto

    @Mapping(source = "blocked", target = "isBlocked")
    fun toShortDto(entity: User): UserShortDto
}