/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type UpdateRoleRequest = {
    userId: string;
    role: UpdateRoleRequest.role;
};

export namespace UpdateRoleRequest {

    export enum role {
        USER = 'USER',
        MODERATOR = 'MODERATOR',
        ADMIN = 'ADMIN',
    }


}
