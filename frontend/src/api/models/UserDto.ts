/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type UserDto = {
    id: string;
    name: string;
    mail: string;
    createdAt: string;
    role: UserDto.role;
    isBlocked: boolean;
};

export namespace UserDto {

    export enum role {
        USER = 'USER',
        MODERATOR = 'MODERATOR',
        ADMIN = 'ADMIN',
    }


}
