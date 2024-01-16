
import { Typography } from '@mui/material'
import { FC, useEffect, useState } from 'react'

type ErrorPageProps = {
    msg?: string 
}

export const ErrorPage: FC<ErrorPageProps> = (props: ErrorPageProps) => {

    const [message, setMessage] = useState<string>('error page')
    useEffect(() => {
        const param = window.location.search
        if (param.length > 1 && param.includes('=')) {
            const queryVal = param.split('=')[1]
            if (queryVal === '401') {
                setMessage("Only authorized users have permission to this resource. Kindly authorize!")
            } 
        } else {
            setMessage(props.msg!)
        }
    }, [])
  return (
    <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
        <Typography variant='h3'>
            {message}
        </Typography>       
    </div>
  )
}