import { action, atom, onMount } from "nanostores";
import { UserApiImplService, UserDto} from "../api";
import { createError, removeToken, token } from "./security";
import { ErrorCode } from "../api/models/ErrorResponse";
import { persistentAtom } from "@nanostores/persistent";

export let currentUser = persistentAtom<UserDto | null>('currentUser', null, {
    decode: JSON.parse,
    encode: JSON.stringify
} 
)

export const loading = atom<boolean>(true)

export let removeUser = action(currentUser, 'remove', store => {
    store.set(null)
})
 

export const addUser = (user: UserDto) => {
    currentUser.set(user)
}

export const fetchUser: () => Promise<UserDto> | null = () => {
    let tokenData = token.get().data
    if (tokenData) {
   
        let headerVal = "Bearer " + tokenData 
        return UserApiImplService.getUserByToken(headerVal).then(r => {
            addUser(r)
            return r
        })
    }

    return null

};

export const fetchUserAndOtherData = (func: Function) => {
    let tokenData = token.get().data
    if (tokenData && !currentUser) {
        let headerVal = "Bearer " + tokenData 
        UserApiImplService.getUserByToken(headerVal).then(r => {
            addUser(r)
            func()
            return r
        })
        .catch(e => {
            createError({body: {message: 'User is unauthorized', code: ErrorCode.EXPIRED_TOKEN}})
            removeToken()
            removeUser()
        })
    }

    else {
        func() 
    }

}

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
                removeUser()
                window.location.href = 'http://localhost:3000'
            })
        } 
    
    }    
})
