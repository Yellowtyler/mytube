/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CommentDto } from './CommentDto';

export type VideoDto = {
    id: string;
    name: string;
    views: number;
    isBlocked: boolean;
    isHidden: boolean;
    videoPath: string;
    comments: Array<CommentDto>;
    likesCount: number;
    isLike?: boolean;
    dislikesCount: number;
    createdAt: string;
    like?: boolean;
};
