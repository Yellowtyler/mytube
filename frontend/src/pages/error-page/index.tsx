
import { Typography } from '@mui/material'
import { FC, useEffect, useState } from 'react'

export const ErrorPage: FC = () => {

    const [message, setMessage] = useState<string>('error page')
    useEffect(() => {
        console.log(window.location)
        const param = window.location.search
        if (param.length > 1 && param.includes('=')) {
            const queryVal = param.split('=')[1]
            if (queryVal === '401') {
                setMessage("Only authorized users have permission to this resource. Kindly authorize!")
            } 
        }
    }, [])
  return (
    <div>
        <Typography variant='h3'>
            {message}
        </Typography>       
    </div>
  )
}