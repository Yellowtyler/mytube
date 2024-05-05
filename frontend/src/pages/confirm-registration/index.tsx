import { useEffect, useState } from 'react'
import { AuthApiImplService } from '../../api'
import { Stack, Typography } from '@mui/material'

type Props = {}

export const ConfirmRegistration = (props: Props) => {

    const [err, setErr] = useState<string>()

    useEffect(() => {
        let token = window.location.pathname.split('/').pop();
        if (!token) {
            setErr("Invalid token!")
        }

        AuthApiImplService.confirmRegistration(token!)
            .catch(e => setErr(e))
    }, [])

  return (
    <div>
        {!err ? 
        <Stack>
            <Typography>
                Your account has been confirmed.
            </Typography>
            <Typography>
                Kindly log in.
            </Typography>
        </Stack>
        : 
        <Typography>{err}</Typography>
        }
    </div>
  )
}