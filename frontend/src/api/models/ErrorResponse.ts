
export type ErrorResponse = {
    message: string | undefined;
    code: ErrorCode
}

export enum ErrorCode {
    USER_NOT_FOUND,
    VIDEO_NOT_FOUND,
    CHANNEL_NOT_FOUND,
    USER_ALREADY_EXISTS,
    WRONG_PASSWORD,
    TOKEN_NOT_FOUND,
    EXPIRED_TOKEN,
    USER_HAS_NO_PERMISSION,
    BAD_PASSWORD,
    VALIDATION_ERROR,
    UNKNOWN_ERROR,
    SERVICE_ERROR
}