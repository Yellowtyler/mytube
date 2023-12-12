import { persistentAtom } from "@nanostores/persistent";
import { action, atom, computed } from "nanostores";
import { AuthApiImplService, LoginRequest } from "../api";
import { ErrorCode, ErrorResponse } from "../api/models/ErrorResponse";

export let openAuth = atom<boolean>(false)

export const openAuthTab = () => openAuth.set(true)

export const closeAuthTab = () => openAuth.set(false)

let data = persistentAtom<string | null>('token', null, {
    decode: (value): string | null => {
        try {
            let tokenData = JSON.parse(value)
            if (typeof tokenData === 'string') {
                return tokenData
            }
            return null
        } catch (e) {
            logout()
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
          throw new Error('UNKNOWN_ERROR')
        }
      } catch (error_) {
        let errorResponseValue = error_ as {
          body?: {
            message: string
            code: ErrorCode
          }
        }
  
        error.set({
            message: errorResponseValue.body?.message,
            code: errorResponseValue.body?.code ?? ErrorCode.UNKNOWN_ERROR        
        })
      } finally {
        loading.set(false)
      }
    },
  )
  
  export let logout = action(data, 'logout', async (store): Promise<void> => {
    let authorization = store.get()
    removeToken()
  
    if (authorization) {
      await AuthApiImplService.logout(authorization)
    }
  
  })