import { Button, Paper, Stack, TextField, Typography } from '@mui/material'
import { FC, useEffect, useRef, useState } from 'react'
import { AuthApiImplService, UserApiImplService, UserDto } from '../../api';
import { useStore } from '@nanostores/react';
import { addUser, currentUser } from '../../stores/current-user';
import { clearError, createError, error, token } from '../../stores/security';
import ErrorAlert from '../../ui/error-alert';
import { ErrorSnackbar } from '../../ui/error-snackbar';
import { useNavigate } from 'react-router-dom';
import styles from './index.module.css'
import AddAPhotoIcon from '@mui/icons-material/AddAPhoto';
import image from '../../icons/mytube.png'
import { getImage, uploadImage } from '../../libs/ImageApi';

export const Settings: FC = () => {

    const tokenVal = useStore(token)
    const currUser = useStore(currentUser)
    const [open, setOpen] = useState<boolean>(false)
    const erroVal = useStore(error)
    const navigate = useNavigate()

    const [newName, setNewName] = useState<string>('')
    const [newMail, setNewMail] = useState<string>('')
    const [selectedFile, setSelectedFile] = useState<any>(image)
    const hiddenFileInput = useRef<HTMLInputElement | null>(null)

    useEffect(() => {
        if (!tokenVal.data) {
            navigate('/error?code=401')
            return
        }
        if (currUser) {
            setNewName(currUser!.name)
            setNewMail(currUser!.mail)
            getImage('profile', currUser.id).then(r => {
                const data = r.data as Blob
                let newVal = data.size === 0 ? image : URL.createObjectURL(data)  
                setSelectedFile(newVal)
            })
        }
    }, [currUser])
    

    const editUser = () => {
        let req = {userId: currUser!.id, name: newName, mail: newMail}
        UserApiImplService.editUser(req).then(r => {
            let editedUser = {...currUser, name: newName, mail: newMail} as UserDto
            addUser(editedUser)
            clearError()
        })
        .catch(e=> createError(e))
    }

    const openDialog = () => {
        hiddenFileInput!.current!.click()
    }

    const handleUploadImage = (event: any) => {
        const file = event.target.files[0]
        uploadImage('profile', currUser!.id, file)
        const fileReader = new FileReader();
        fileReader.addEventListener("load", () => {
            setSelectedFile(fileReader.result)
        });
        fileReader.readAsDataURL(file);
    }

  return (
    <div style={{display:'flex', justifyContent: 'center'}}>
    <Paper style={{backgroundColor: 'whitesmoke', width: '500px', margin: '1rem', justifyContent: 'center'}} elevation={2}>
        <Stack alignContent={'center'} alignItems={'center'} padding={'1rem'} spacing={2}>
        <Typography variant='h4'>Account settings</Typography> 
        <div className={styles['container']} >
            <img 
                className={styles['image']}
                src={selectedFile} 
            />
            <div className={styles['overlay']} onClick={openDialog}>
                <AddAPhotoIcon fontSize='large'/>
            </div>
            <input
                ref={hiddenFileInput} 
                type='file'
                accept='image/*'
                style={{display:'none'}}
                onChange={(e: any) =>handleUploadImage(e)}
            />
        </div>
        <TextField 
            value={newName}
            type='text'
            label='Username' 
            placeholder='Enter new name'
            onChange={(e:any) => setNewName(e.target.value)}
        />
        <TextField 
            value={newMail}
            type='text'
            label='Mail'
            placeholder='Enter new mail'
            onChange={(e:any) => setNewMail(e.target.value)}
        />
        <Button onClick={editUser} variant='contained' style={{paddingLeft:'5.5rem', paddingRight: '5.5rem'}}>Save</Button>
       {open && <ChangePassword setOpen={setOpen} />}
        {!open && <Button variant='contained' style={{paddingLeft:'2rem', paddingRight: '2rem'}} onClick={(e:any) => setOpen(true)}>Change password</Button>}
    </Stack>
    </Paper>
        {erroVal && <ErrorSnackbar/>}
    </div>
  )
}

type ChangePasswordProps = {
    setOpen: (v: boolean) => void;
}

const ChangePassword: FC<ChangePasswordProps> = (props: ChangePasswordProps) => {
    const erroVal = useStore(error)
    const [oldPassword, setOldPassword] = useState<string>('')
    const [newPassword, setNewPassword] = useState<string>('')

    const changePassword = () => {
        let req = {oldPassword: oldPassword, newPassword: newPassword}
        AuthApiImplService.changePassword(req)
        .then(r=>{
            props.setOpen(false)
            clearError()
        })
        .catch(e=>createError(e))
    }
  
    return (
    <Stack spacing={1}>
        <TextField
            label='Old password'
            placeholder='Enter old password'
            fullWidth
            value={oldPassword}
            onChange={(e: any)=>setOldPassword(e.target.value)}
        />
        <TextField
            label='New password'
            placeholder='Enter new password'
            fullWidth
            value={newPassword}
            onChange={(e: any)=>setNewPassword(e.target.value)}
        />
        <Stack direction='row' spacing={1} alignItems={'center'}>
        <Button variant='contained' fullWidth onClick={changePassword}>Apply</Button>
        <Button variant='contained' fullWidth onClick={(e: any) => props.setOpen(false)}>Cancel</Button>
        </Stack>
        {erroVal && <ErrorAlert/>}
    </Stack>
  )
}