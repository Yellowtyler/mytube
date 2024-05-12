/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { LikeDto } from '../models/LikeDto';
import type { LikeVideoRequest } from '../models/LikeVideoRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class LikeApiImplService {

    /**
     * @param requestBody 
     * @returns LikeDto OK
     * @throws ApiError
     */
    public static likeVideo(
requestBody: LikeVideoRequest,
): CancelablePromise<LikeDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/like',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param videoId 
     * @returns void 
     * @throws ApiError
     */
    public static deleteLike(
videoId: string,
): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/like/{videoId}',
            path: {
                'videoId': videoId,
            },
        });
    }

}
