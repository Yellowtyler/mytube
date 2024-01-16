import axios from "axios";
import { OpenAPI } from "../api";
import { token } from "../stores/security";

export const VIDEO_API_URL = OpenAPI.BASE + '/api/video';

export const uploadVideo = (name: string, file: File) => {
    const formData = new FormData() 
    formData.append("video", file) 
    formData.append("name", name)
    console.log(token.get().data)
    return axios.post(VIDEO_API_URL, formData, {headers: {'Authorization': 'Bearer ' + token.get().data, "Content-Type": 'multipart/form-data'}})
}

export const getVideo = (videoId: string) => {
    return axios.get(VIDEO_API_URL + `/${videoId}`, { responseType: 'blob' })
}