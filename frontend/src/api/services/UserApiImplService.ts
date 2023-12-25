/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EditUserRequest } from '../models/EditUserRequest';
import type { GetUsersRequest } from '../models/GetUsersRequest';
import type { GetUsersResponse } from '../models/GetUsersResponse';
import type { UpdateRoleRequest } from '../models/UpdateRoleRequest';
import type { UserDto } from '../models/UserDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class UserApiImplService {

    /**
     * @param requestBody 
     * @returns any OK
     * @throws ApiError
     */
    public static editUser(
requestBody: EditUserRequest,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/user',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param requestBody 
     * @returns any successfully updated user role
     * @throws ApiError
     */
    public static updateRole(
requestBody: UpdateRoleRequest,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/user/role',
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
     * @param userId 
     * @returns any successfully blocked user
     * @throws ApiError
     */
    public static blockUser(
userId: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/user/block/{userId}',
            path: {
                'userId': userId,
            },
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this id wasn't found`,
            },
        });
    }

    /**
     * @param id 
     * @returns UserDto successfully received user
     * @throws ApiError
     */
    public static getUser(
id: string,
): CancelablePromise<UserDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/user/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this id wasn't found`,
            },
        });
    }

    /**
     * @param authorization 
     * @returns UserDto successfully received user
     * @throws ApiError
     */
    public static getUserByToken(
authorization: string,
): CancelablePromise<UserDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/user/token',
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this id wasn't found`,
            },
        });
    }

    /**
     * @param req 
     * @returns GetUsersResponse successfully received users
     * @throws ApiError
     */
    public static getUsers(
req: GetUsersRequest,
): CancelablePromise<GetUsersResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/user/all',
            query: {
                'req': req,
            },
            errors: {
                401: `User is unauthorized`,
                403: `User doesn't have permission to resource`,
                404: `User with this id wasn't found`,
            },
        });
    }

}
