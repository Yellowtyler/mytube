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
     * @param requestBody 
     * @returns any OK
     * @throws ApiError
     */
    public static uploadPhoto(
type: string,
requestBody?: {
image: Blob;
},
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/image/upload/{type}',
            path: {
                'type': type,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param type 
     * @returns string OK
     * @throws ApiError
     */
    public static getImage(
type: string,
): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/image/{type}',
            path: {
                'type': type,
            },
        });
    }

}
