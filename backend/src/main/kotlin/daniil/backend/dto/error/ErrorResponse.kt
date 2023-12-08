package daniil.backend.dto.error

import daniil.backend.enums.ErrorCode

data class ErrorResponse(val errorCode: ErrorCode, val message: String)
