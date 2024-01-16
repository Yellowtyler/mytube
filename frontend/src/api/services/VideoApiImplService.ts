/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EditVideoRequest } from '../models/EditVideoRequest';
import type { GetVideosRequest } from '../models/GetVideosRequest';
import type { UploadVideoResponse } from '../models/UploadVideoResponse';
import type { VideoDto } from '../models/VideoDto';
import type { VideoShortDto } from '../models/VideoShortDto';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class VideoApiImplService {

    /**
     * @param requestBody 
     * @returns VideoDto OK
     * @throws ApiError
     */
    public static editVideo(
requestBody: EditVideoRequest,
): CancelablePromise<VideoDto> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/video',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param channelId 
     * @param name 
     * @param requestBody 
     * @returns UploadVideoResponse OK
     * @throws ApiError
     */
    public static uploadVideo(
channelId: string,
name: string,
requestBody?: {
video: Blob;
},
): CancelablePromise<UploadVideoResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/video',
            query: {
                'channelId': channelId,
                'name': name,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param id 
     * @returns VideoDto OK
     * @throws ApiError
     */
    public static blockVideo(
id: string,
): CancelablePromise<VideoDto> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/video/block/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * @param videoId 
     * @returns any OK
     * @throws ApiError
     */
    public static incrementViews(
videoId: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/video/views',
            query: {
                'videoId': videoId,
            },
        });
    }

    /**
     * @param videoId 
     * @returns string OK
     * @throws ApiError
     */
    public static getVideo(
videoId: string,
): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/video/{videoId}',
            path: {
                'videoId': videoId,
            },
        });
    }

    /**
     * @param id 
     * @returns VideoDto OK
     * @throws ApiError
     */
    public static getVideoInfo(
id: string,
): CancelablePromise<VideoDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/video/info/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * @param req 
     * @returns VideoShortDto OK
     * @throws ApiError
     */
    public static getVideos(
req: GetVideosRequest,
): CancelablePromise<Array<VideoShortDto>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/video/all',
            query: {
                'req': req,
            },
        });
    }

    /**
     * @param id 
     * @returns void 
     * @throws ApiError
     */
    public static deleteVideo(
id: string,
): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/video/{id}',
            path: {
                'id': id,
            },
        });
    }

}
