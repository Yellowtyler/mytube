
import { Button, Dialog, Paper, Stack, TextField, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { AuthApiImplService } from '../../api'
import { createError, error } from '../../stores/security'
import { useStore } from '@nanostores/react'
import ErrorAlert from '../../ui/error-alert'
import { useNavigate } from 'react-router-dom'

type Props = {}

const ResetPassword = (props: Props) => {

    const [token, setToken] = useState<string>('')
    const [newPassword, setNewPassword] = useState<string>('')
    const errorVal = useStore(error)
    const navigate = useNavigate()

    useEffect(() => {
        setToken(window.location.pathname.split("/")[2])
    }, [])

    const resetPassword = () => {
        let req = {token: token, newPassword: newPassword}
        AuthApiImplService.resetPassword(req)
        .then(r => navigate('/'))
        .catch(e => createError(e))
    }

    return (
    <div>
        <Paper sx={{marginLeft: 'auto', marginRight: 'auto', maxWidth: '30%', marginTop: 10}} elevation={0} >
            <Stack spacing={3}>
                <Typography align='center' fontSize={30}>Reset password</Typography>
                <TextField
                    value={newPassword}
                    onChange={(e: any) => setNewPassword(e.target.value)}
                    label='Enter new password'
                    fullWidth
                    autoFocus
                    margin='dense'
                    type='text'
                    variant='standard'
                />
                <Button variant='contained' onClick={resetPassword}>Reset</Button>
                {errorVal && <ErrorAlert/>}
            </Stack>
        </Paper>
    </div>
  )
}

export default ResetPassword