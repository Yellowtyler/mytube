/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ChangePasswordRequest } from '../models/ChangePasswordRequest';
import type { ForgotPasswordRequest } from '../models/ForgotPasswordRequest';
import type { LoginRequest } from '../models/LoginRequest';
import type { LoginResponse } from '../models/LoginResponse';
import type { RegisterRequest } from '../models/RegisterRequest';
import type { ResetPasswordRequest } from '../models/ResetPasswordRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AuthApiImplService {

    /**
     * @param requestBody 
     * @returns any Reset password request was successfully sent
     * @throws ApiError
     */
    public static resetPassword(
requestBody: ResetPasswordRequest,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/auth/reset-password',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this name wasn't found`,
            },
        });
    }

    /**
     * @param requestBody 
     * @returns any Register request was successfully sent
     * @throws ApiError
     */
    public static register(
requestBody: RegisterRequest,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/auth/register',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this id wasn't found`,
            },
        });
    }

    /**
     * @param authorization 
     * @returns any User was successfully logged out
     * @throws ApiError
     */
    public static logout(
authorization: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/auth/logout',
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this name wasn't found`,
            },
        });
    }

    /**
     * @param requestBody 
     * @returns LoginResponse User was successfully logged in
     * @throws ApiError
     */
    public static login(
requestBody: LoginRequest,
): CancelablePromise<LoginResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/auth/login',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this name wasn't found`,
            },
        });
    }

    /**
     * @param requestBody 
     * @returns any reset password mail was successfully send
     * @throws ApiError
     */
    public static forgotPassword(
requestBody: ForgotPasswordRequest,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/auth/forgot-password',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this name wasn't found`,
            },
        });
    }

    /**
     * @param token 
     * @returns any User was successfully registered
     * @throws ApiError
     */
    public static confirmRegistration(
token: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/auth/confirm-registration/{token}',
            path: {
                'token': token,
            },
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this id wasn't found`,
            },
        });
    }

    /**
     * @param requestBody 
     * @returns any Password was changed successfully
     * @throws ApiError
     */
    public static changePassword(
requestBody: ChangePasswordRequest,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/auth/change-password',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this name wasn't found`,
            },
        });
    }

}
