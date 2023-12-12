/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type UserShortDto = {
    id: string;
    name: string;
    role: UserShortDto.role;
    isBlocked: boolean;
};

export namespace UserShortDto {

    export enum role {
        USER = 'USER',
        MODERATOR = 'MODERATOR',
        ADMIN = 'ADMIN',
    }


}
