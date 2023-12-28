import axios from "axios";
import { OpenAPI } from "../api";
import { token } from "../stores/security";

export const IMAGE_API_URL = OpenAPI.BASE + '/api/image';

export const uploadImage = (type: string, userId: string, file: File) => {
    const formData = new FormData() 
    formData.append("image", file) 
    formData.append("type", type)
    formData.append("user", userId)
    return axios.post(IMAGE_API_URL + `/upload`, formData, {headers: {'Authorization': 'Bearer ' + token.get().data}})
}

export const getImage = (type: string, userId: string) => {
    return axios.get(IMAGE_API_URL + `?type=${type}&user=${userId}`, {headers: {'Authorization': 'Bearer ' + token.get().data}, responseType: 'blob' })
} 