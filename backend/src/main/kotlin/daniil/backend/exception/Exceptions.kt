package daniil.backend.exception

import daniil.backend.enums.ErrorCode


abstract class AbstractException(val errorCode: ErrorCode, override val message: String): Exception()

open class NotFoundException(errorCode: ErrorCode, message: String): AbstractException(errorCode, message)

class UserNotFoundException(override val message: String) : NotFoundException(
    errorCode = ErrorCode.USER_NOT_FOUND, message = message
)

class TokenNotFoundException(override val message: String) : NotFoundException(
    errorCode = ErrorCode.TOKEN_NOT_FOUND, message = message
)

class ChannelNotFoundException(override val message: String): NotFoundException(
    errorCode = ErrorCode.CHANNEL_NOT_FOUND, message = message
)

class UserAlreadyExistsException(override val message: String) : AbstractException(
    errorCode = ErrorCode.USER_ALREADY_EXISTS, message = message
)

class WrongPasswordException(override val message: String) : AbstractException(
    errorCode = ErrorCode.WRONG_PASSWORD, message = message
)

class ExpiredTokenException(override val message: String) : AbstractException(
    errorCode = ErrorCode.EXPIRED_TOKEN, message = message
)

class UserHasNoPermissionException(override val message: String): AbstractException(
    errorCode = ErrorCode.USER_HAS_NO_PERMISSION, message = message
)

class BadPasswordException(override val message: String): AbstractException(
    errorCode = ErrorCode.BAD_PASSWORD, message = message
)

class TokenAlreadyExistsException(override val message: String): AbstractException(
   errorCode = ErrorCode.TOKEN_ALREADY_EXISTS, message = message
)

class UserNotRegisteredException(override val message: String): AbstractException(
   errorCode = ErrorCode.USER_NOT_REGISTERED, message = message
)

class UserBlockedException(override val message: String): AbstractException(
    errorCode = ErrorCode.BLOCKED_USER, message = message
)