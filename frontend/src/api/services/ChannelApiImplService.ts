/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BlockChannelRequest } from '../models/BlockChannelRequest';
import type { ChannelDto } from '../models/ChannelDto';
import type { EditChannelRequest } from '../models/EditChannelRequest';
import type { GetChannelsRequest } from '../models/GetChannelsRequest';
import type { GetChannelsResponse } from '../models/GetChannelsResponse';
import type { SubscribeChannelRequest } from '../models/SubscribeChannelRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ChannelApiImplService {

    /**
     * @returns ChannelDto OK
     * @throws ApiError
     */
    public static getChannel(): CancelablePromise<ChannelDto> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/channel',
        });
    }

    /**
     * @param requestBody 
     * @returns any OK
     * @throws ApiError
     */
    public static editChannel(
requestBody: EditChannelRequest,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/channel',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param requestBody 
     * @returns any OK
     * @throws ApiError
     */
    public static subscribe(
requestBody: SubscribeChannelRequest,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/channel/subscribe',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param requestBody 
     * @returns any OK
     * @throws ApiError
     */
    public static blockChannel(
requestBody: BlockChannelRequest,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/channel/block',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @param req 
     * @returns GetChannelsResponse OK
     * @throws ApiError
     */
    public static getChannels(
req: GetChannelsRequest,
): CancelablePromise<GetChannelsResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/channel/all',
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
    public static deleteChannel(
id: string,
): CancelablePromise<void> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/channel/{id}',
            path: {
                'id': id,
            },
        });
    }

}
