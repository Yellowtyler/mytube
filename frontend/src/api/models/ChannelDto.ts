/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { UserDto } from './UserDto';

export type ChannelDto = {
    id: string;
    name: string;
    createdAt: string;
    isBlocked: boolean;
    owner: UserDto;
    subscribersCount: number;
    blocked?: boolean;
};
