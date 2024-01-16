import { IconButton, Snackbar } from '@mui/material'
import React, { FC, useEffect, useState } from 'react'
import { error } from '../../stores/security'
import { useStore } from '@nanostores/react'
import CloseIcon from '@mui/icons-material/Close';

export const ErrorSnackbar: FC = () => {

    const [open, setOpen] = useState<boolean>(false)
    const errVal = useStore(error)

    const action = (
        <React.Fragment>
        <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={(e:any)=>setOpen(false)}
        >
            <CloseIcon fontSize="small" />
        </IconButton>
        </React.Fragment>
    );

    useEffect(() => {
        setOpen(true)
    }, [errVal])

    return (
        <Snackbar
            open={open}
            autoHideDuration={6000}
            onClose={(e:any) => setOpen(false)}
            message={errVal?.message}
            action={action}
            color='primary'
        /> 
    )
}