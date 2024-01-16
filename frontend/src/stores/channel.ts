import { atom, onMount } from "nanostores";
import { ChannelDto } from "../api";
import { token } from "./security";

export const channel = atom<ChannelDto | null>(null)

export const loading = atom<boolean>(true)

export const addChannel = (newChannel: ChannelDto) => {
    channel.set(newChannel)
}

// onMount(channel, () => {
//     if (loading.get()) {
//         let tokenData = token.get().data
//         if (tokenData) {

//         }
//     }
// })