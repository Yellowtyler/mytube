import { atom, onMount } from "nanostores";
import { UserApiImplService, UserDto} from "../api";
import { createError, logout, removeToken, token } from "./security";
import { ErrorCode } from "../api/models/ErrorResponse";

export const currentUser = atom<UserDto | null>(null)

export const loading = atom<boolean>(true)

export const addUser = (user: UserDto) => {
    currentUser.set(user)
}

export const fetchUser: () => Promise<void | UserDto> | null = () => {
    let tokenData = token.get().data
    if (tokenData) {
        let headerVal = "Bearer " + tokenData 
        return UserApiImplService.getUserByToken(headerVal).then(r => {
            addUser(r)
            return r
        })
        .catch(e => console.log(e))
    }

    return null

};

onMount(currentUser, () => {
    let loadingValue = loading.get()
    if (loadingValue) {
        let tokenData = token.get().data
        if (tokenData) {
            let headerVal = "Bearer " + tokenData 
            UserApiImplService.getUserByToken(headerVal).then(r => {
                addUser(r)
                loading.set(false)
            })
            .catch(e => {
                createError({body: {message: 'User is unauthorized', code: ErrorCode.EXPIRED_TOKEN}})
                removeToken()
                window.location.href = 'http://localhost:3000'
            })
        } 
    
    }    
})
