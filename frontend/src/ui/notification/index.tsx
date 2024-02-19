import React, { FC} from 'react'
import { IconButton, Snackbar } from '@mui/material'
import CloseIcon from '@mui/icons-material/Close';

type NotificationProps = {
    msg: String;
    open: boolean;
    setOpen: (val: boolean) => void;
    func: Function;
}

export const Notification: FC<NotificationProps> = (props: NotificationProps) => {

    const closeNotification = () => {
        props.setOpen(false)
        props.func()
    } 

    const action = (
        <React.Fragment>
        <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={closeNotification}
        >
            <CloseIcon fontSize="small" />
        </IconButton>
        </React.Fragment>
    );

    return (
        <Snackbar
            open={props.open}
            autoHideDuration={6000}
            message={props.msg}
            onClose={closeNotification}
            action={action}
            color='inherit'
        /> 
    )
}