package daniil.backend.extension

import daniil.backend.enums.UserRole
import daniil.backend.exception.TokenNotFoundException
import daniil.backend.exception.UserNotFoundException
import org.springframework.security.core.Authentication

fun throwUserNotFound(arg: Any): Nothing {
    when (arg) {
        is String -> throw UserNotFoundException("user $arg wasn't found")
        else -> throw UserNotFoundException("user with id=${arg.toString()} wasn't found")
    }
}


fun throwTokenNotFound(token: String): Nothing {
    throw TokenNotFoundException("token $token wasn't found")
}

fun getRole(auth: Authentication): UserRole {
    return auth.authorities
        .stream()
        .map {
            when(it.authority) {
                "ROLE_USER" -> UserRole.USER
                "ROLE_MODERATOR" -> UserRole.MODERATOR
                else -> UserRole.ADMIN
            }
        }
        .findFirst()
        .get()
}