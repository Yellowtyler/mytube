import axios from "axios";
import { GetVideosRequest, OpenAPI } from "../api";
import { token } from "../stores/security";

export const VIDEO_API_URL = OpenAPI.BASE + '/api/video';

export const uploadVideo = (name: string, poster: string, file: File) => {
    const formData = new FormData() 
    formData.append("video", file) 
    formData.append("name", name)
    formData.append("poster", poster)
    console.log(token.get().data)
    return axios.post(VIDEO_API_URL, formData, {headers: {'Authorization': 'Bearer ' + token.get().data, "Content-Type": 'multipart/form-data'}})
}

export const getVideo = (videoId: string) => {
    return axios.get(VIDEO_API_URL + `/${videoId}`, { responseType: 'blob' })
}

export const getVideos = (req: GetVideosRequest) => {

    let query = `page=${req.page}&size=${req.size}`
    if (req.channelId) {
        query += `&channelId=${req.channelId}`
    }

    if (req.name) {
        query += `&name=${req.name}`
    }

    let headers
    if (token.get().data) {
        console.log(1)
        headers = {headers: {'Authorization': 'Bearer ' + token.get().data}}
    }

    return axios.get(VIDEO_API_URL + `/all?${query}`, headers)
}