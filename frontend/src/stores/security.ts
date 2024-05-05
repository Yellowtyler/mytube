import { persistentAtom } from "@nanostores/persistent";
import { action, atom, computed } from "nanostores";
import { ApiError, AuthApiImplService, LoginRequest, OpenAPI } from "../api";
import { ErrorCode, ErrorResponse } from "../api/models/ErrorResponse";
import { AxiosError } from "axios";
import { removeUser } from "./current-user";


let data = persistentAtom<string | null>('token', null, {
    decode: (value): string | null => {
      try {
            let tokenData = JSON.parse(value)
            if (typeof tokenData === 'string') {
                return tokenData
            }
            return null
        } catch (e) {
            removeToken()
            removeUser()
            return null
        }
    },
    encode: JSON.stringify
} 
) 

let loading = atom<boolean>(false)

export let error = atom<ErrorResponse| null>(null)

export let token = computed(
    [data, loading, error],
    (dataValue, loadingValue, errorValue) => ({
        loading: loadingValue,
        error: errorValue,
        data: dataValue
    })
)


export let addToken = action(data, 'add', (store, tokenValue: string) => {
    store.set(tokenValue)
    OpenAPI.TOKEN = tokenValue
})
  
export let removeToken = action(data, 'remove', store => {
    store.set(null)
})
  
export let auth = action(
  data,
  'auth',
  async (store, req: LoginRequest): Promise<void> => {
    loading.set(true)

    try {
      let { token: tokenValue } = await AuthApiImplService.login(req)
      if (tokenValue) {
        addToken(tokenValue)
      } else {
        throw new Error('TOKEN_NOT_FOUND')
      }

    } catch (error_) {
      createError(error_)
      return
    } finally {
      loading.set(false)
    }

    error.set(null)
  }
)

export let logout = action(data, 'logout', async (store): Promise<void> => {
  let authorization = 'Bearer ' + store.get()
  removeToken()
  removeUser()

  if (authorization) {
      await AuthApiImplService.logout(authorization)
  }

})

export const createError = (error_: any) => {
    let errorResponseValue = {} as {
          message: string
          code: ErrorCode
        }
    

    if (typeof error_ === 'string') {
        errorResponseValue = {message: error_, code: ErrorCode.VALIDATION_ERROR}
    }

    else if (error_ instanceof ApiError) {
        errorResponseValue = {message: error_.body.message, code: error_.body.errorCode}
    }

    else if (error_ instanceof AxiosError) {
        if (error_.message === 'Network Error') {
            errorResponseValue = {message: "Maximum upload size exceeded (100 MB)", code: ErrorCode.SERVICE_ERROR}
        } else if (error_.response?.data.message) {
            errorResponseValue = {message: error_.response?.data.message, code: error_.response?.data.code}
        } else {
            errorResponseValue = {message: error_.message, code: ErrorCode.EXPIRED_TOKEN}
        }
    }

    else {
        errorResponseValue = {message: "Service is unavailable", code: ErrorCode.SERVICE_ERROR}
    }
    
    error.set({
        message: errorResponseValue.message,
        code: errorResponseValue.code ?? ErrorCode.UNKNOWN_ERROR        
    })
}

export const clearError = () => error.set(null)