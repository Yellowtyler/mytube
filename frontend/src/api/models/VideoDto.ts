/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CommentDto } from './CommentDto';
import type { VideoOwnerDto } from './VideoOwnerDto';

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
    channel: VideoOwnerDto;
    like?: boolean;
};
