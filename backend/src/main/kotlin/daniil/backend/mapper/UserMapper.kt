package daniil.backend.mapper

import daniil.backend.dto.user.UserDto
import daniil.backend.dto.user.UserShortDto
import daniil.backend.entity.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDto(entity: User): UserDto

    fun toShortDto(entity: User): UserShortDto
}