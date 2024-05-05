import { atom} from "nanostores";
import { ChannelDto } from "../api";

export const channel = atom<ChannelDto | null>(null)

export const loading = atom<boolean>(true)

export const addChannel = (newChannel: ChannelDto) => {
    channel.set(newChannel)
}
