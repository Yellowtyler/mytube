/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ImageApiImplService {

    /**
     * @param type 
     * @param user 
     * @param requestBody 
     * @returns any OK
     * @throws ApiError
     */
    public static uploadPhoto(
type: string,
user: string,
requestBody?: {
image: Blob;
},
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/image/upload',
            query: {
                'type': type,
                'user': user,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param type 
     * @param user 
     * @param video 
     * @returns string OK
     * @throws ApiError
     */
    public static getImage(
type: string,
user: string,
video?: string,
): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/image',
            query: {
                'type': type,
                'user': user,
                'video': video,
            },
        });
    }

}
