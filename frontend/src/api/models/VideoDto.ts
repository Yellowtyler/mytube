/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { ChannelInfoDto } from './ChannelInfoDto';
import type { CommentDto } from './CommentDto';

export type VideoDto = {
    id: string;
    name: string;
    views: number;
    isBlocked: boolean;
    isHidden: boolean;
    comments: Array<CommentDto>;
    likesCount: number;
    isLike?: boolean;
    dislikesCount: number;
    createdAt: string;
    channel: ChannelInfoDto;
    like?: boolean;
};
