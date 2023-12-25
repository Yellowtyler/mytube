import axios from "axios";
import { OpenAPI } from "../api";
import { token } from "../stores/security";

export const IMAGE_API_URL = OpenAPI.BASE + '/api/image';

export const uploadImage = (type: string, file: File) => {
    const formData = new FormData();
    formData.append("image", file);
    return axios.post(IMAGE_API_URL + `/upload/${type}`, formData, {headers: {'Authorization': 'Bearer ' + token.get().data}})
}

export const getImage = (type: string) => {
    return axios.get(IMAGE_API_URL + `/${type}`, {headers: {'Authorization': 'Bearer ' + token.get().data}, responseType: 'blob' })
} 