package daniil.backend.handler

import daniil.backend.dto.error.ErrorResponse
import daniil.backend.enums.ErrorCode
import daniil.backend.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import java.util.stream.Collectors

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.errorCode, e.message))
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(e: UserAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.errorCode, e.message))
    }

    @ExceptionHandler(UserAlreadySubscribedException::class)
    fun handleUserAlreadySubscribedException(e: UserAlreadySubscribedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.errorCode, e.message))
    }


    @ExceptionHandler(TokenAlreadyExistsException::class)
    fun handleTokenAlreadyExistsException(e: TokenAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.errorCode, e.message))
    }

    @ExceptionHandler(WrongPasswordException::class)
    fun handleWrongPasswordException(e: WrongPasswordException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(ErrorCode.WRONG_PASSWORD, e.message))
    }

    @ExceptionHandler(UserHasNoPermissionException::class)
    fun handleUserHasNoPermissionException(e: UserHasNoPermissionException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse(ErrorCode.USER_HAS_NO_PERMISSION, e.message))
    }

    @ExceptionHandler(UserNotRegisteredException::class)
    fun handleUserNotRegisteredException(e: UserNotRegisteredException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(e.errorCode, e.message))
    }

    @ExceptionHandler(UserBlockedException::class)
    fun handleUserBlockedException(e: UserBlockedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse(e.errorCode, e.message))
    }

    @ExceptionHandler(ExpiredTokenException::class)
    fun handleExpiredTokenException(e: ExpiredTokenException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(ErrorCode.EXPIRED_TOKEN, e.message))
    }

    @ExceptionHandler(BadPasswordException::class)
    fun handleBadPasswordException(e: BadPasswordException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ErrorCode.BAD_PASSWORD, e.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val description = ex.bindingResult
            .fieldErrors
            .stream()
            .map { e: FieldError -> e.field + " " + e.defaultMessage }
            .collect(Collectors.joining(", "))
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ErrorCode.VALIDATION_ERROR, description))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ErrorCode.VALIDATION_ERROR, e.localizedMessage))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(ErrorCode.SERVICE_ERROR, Optional.ofNullable(e.message).orElse("Internal Service Error")))
    }

    @ExceptionHandler(VideoIsHiddenException::class)
    fun handleVideoIsHiddenException(e: VideoIsHiddenException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse(e.errorCode, e.message))
    }

    @ExceptionHandler(VideoIsBlockedException::class)
    fun handleVideoIsBlockedException(e: VideoIsBlockedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse(e.errorCode, e.message))
    }
}
