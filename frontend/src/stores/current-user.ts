import { atom, onMount } from "nanostores";
import { UserApiImplService, UserShortDto } from "../api";
import { token } from "./security";

export const currentUser = atom<UserShortDto | null>(null)

export const loading = atom<boolean>(true)

export const addUser = (user: UserShortDto) => {
    currentUser.set(user)
}

export const fetchUser: () => Promise<void | UserShortDto> | null = () => {
    let tokenData = token.get().data
    if (tokenData) {
        return UserApiImplService.getUserByToken().then(r => {
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
            UserApiImplService.getUserByToken().then(r => {
                addUser(r)
                loading.set(false)
            })
            .catch(e => console.log(e))
        }   
    }    
})
