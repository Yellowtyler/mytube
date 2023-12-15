import { Alert } from '@mui/material'
import { useStore } from '@nanostores/react'
import { error } from '../../stores/security'


const ErrorAlert = () => {

  let errorMessage = useStore(error)

  return (
    <Alert severity="error">{errorMessage?.message}</Alert>
  )
}

export default ErrorAlert