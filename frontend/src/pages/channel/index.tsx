import { FC, useEffect, useRef, useState } from 'react'
import { ChannelApiImplService, ChannelDto, EditChannelRequest, OpenAPI } from '../../api'
import { createError, error, token } from '../../stores/security'
import { useStore } from '@nanostores/react'
import { currentUser, loading } from '../../stores/current-user'
import { Button, Divider, Stack, TextField, Typography } from '@mui/material'
import { getImage, uploadImage } from '../../libs/ImageApi'
import image from '../../icons/mytube.png'
import background from '../../icons/default_background.jpg'
import styles from './index.module.css'
import AddAPhotoIcon from '@mui/icons-material/AddAPhoto';
import EditIcon from '@mui/icons-material/Edit';
import { ErrorSnackbar } from '../../ui/error-snackbar'
import DoneIcon from '@mui/icons-material/Done';

export const Channel: FC = () => {
    
    const currUser = useStore(currentUser)
    const load = useStore(loading)
    const tokenVal = useStore(token)
    const errorVal = useStore(error)

    const [channel, setChannel] = useState<ChannelDto>()
    const [profilePhoto, setProfilePhoto] = useState<any>(image)
    const [backgroundPhoto, setBackgroundPhoto] = useState<any>(background)
    const [isOwnChannel, setIsOwnChannel] = useState<boolean>(false)
    const [editChannelReq, setEditChannelReq] = useState<EditChannelRequest>({id: '', name: '', description: ''})
    const [edit, setEdit] = useState<boolean>(false)

    const hiddenProfileInput = useRef<HTMLInputElement>(null)
    const hiddenBackgroundInput = useRef<HTMLInputElement>(null)

    useEffect(() => {
        if (!load) {
            OpenAPI.TOKEN = tokenVal!.data!  
            let id = window.location.pathname.split('/').pop();
            ChannelApiImplService.getChannel(id!)
            .then(r => {
                setChannel(r)
                if (r.id === currUser?.channelId!) {
                    setIsOwnChannel(false)
                    setEditChannelReq({id: r.id, name: r.name, description: r.description})
                }
            })
            .catch(e => createError(e))

            getImage('profile', currUser!.id).then(r => {
                const data = r.data as Blob
                let newVal = data.size === 0 ? image : URL.createObjectURL(data)  
                setProfilePhoto(newVal)
            })
            .catch(e => createError(e))

            getImage('background', currUser!.id).then(r => {
                const data = r.data as Blob
                let newVal = data.size === 0 ? background : URL.createObjectURL(data)  
                setBackgroundPhoto(newVal)
            })
            .catch(e => createError(e))
 
        }
   }, [load])
    
    const openProfilePhotoDialog = () => {
        hiddenProfileInput!.current!.click()
    }
    
    const openBackgroundPhotoDialog = () => {
        hiddenBackgroundInput!.current!.click()
    }

    const handleUploadImage = (event: any, type: string) => {
        const file = event.target.files[0]
        uploadImage(type, currUser!.id, file)
        const fileReader = new FileReader();
        fileReader.addEventListener("load", () => {
            if (type === 'profile') {
                setProfilePhoto(fileReader.result)
            } else {
                setBackgroundPhoto(fileReader.result)
            }
        });
        fileReader.readAsDataURL(file);
    }

    const handleEditClick = () => setEdit(true)

    const handleEditChannel = () => {
        ChannelApiImplService.editChannel(editChannelReq)
        .then(r => {
            setEdit(false)
            setChannel((prev: any) => ({...prev, name: editChannelReq.name, description: editChannelReq.description}))
        })
        .catch(e => createError(e))
    }

    return (

        <div className={styles['box']}>
            <div>
           <div className={styles['background-container']}>
                <img className={styles['background']} src={backgroundPhoto}/>
                {isOwnChannel && <div className={styles['overlay-background']} onClick={openBackgroundPhotoDialog}>
                <AddAPhotoIcon fontSize='large'/>
                </div>}
            </div>
            
            <Stack direction={'row'} marginBottom={'1rem'}>
                <div className={styles['profile-container']}>
                    <img className={styles['image']} src={profilePhoto}/>
                    {isOwnChannel && <div className={styles['overlay-profile']} onClick={openProfilePhotoDialog}>
                        <AddAPhotoIcon fontSize='large'/>
                    </div>}
                    <input
                        ref={hiddenProfileInput} 
                        type='file'
                        accept='image/*'
                        style={{display:'none'}}
                        onChange={(e: any) =>handleUploadImage(e, 'profile')}
                    />
                    <input
                        ref={hiddenBackgroundInput} 
                        type='file'
                        accept='image/*'
                        style={{display:'none'}}
                        onChange={(e: any) =>handleUploadImage(e, 'background')}
                    />
                </div>
                <Stack direction={'column'} spacing={1}>
                    {isOwnChannel && !edit && <Stack direction={'row'}>
                        <Typography variant='h4'>{channel?.name}</Typography>
                        <EditIcon style={{cursor:'pointer'}} onClick={handleEditClick}/>
                    </Stack>
                    }
                     {isOwnChannel && edit && <Stack direction={'row'}>
                        <TextField 
                            value={editChannelReq.name} 
                            placeholder='Enter new channel name'
                            label='Channel name'
                            onChange={(e: any) => setEditChannelReq(prev=>({...prev, name: e.target.value}))}
                        />
                        <DoneIcon style={{cursor:'pointer'}} onClick={handleEditChannel}/>
                    </Stack>
                    }
                    {!isOwnChannel && <Typography variant='h4'>{channel?.name}</Typography>}
                    <Typography>{channel?.subscribersCount + ' subscribers ‧ ' + channel?.videosCount + ' videos'}</Typography>
                    {isOwnChannel && !edit && <Stack direction={'row'}>
                        <Typography variant='body2'>{channel?.description}</Typography>
                        <EditIcon style={{cursor:'pointer'}} onClick={handleEditClick}/>
                    </Stack>
                    }
                     {isOwnChannel && edit && <Stack direction={'row'}>
                        <TextField 
                            value={editChannelReq.description} 
                            placeholder='Enter new channel description'
                            label='Description'
                            onChange={(e: any) => setEditChannelReq(prev=>({...prev, description: e.target.value}))}
                        />
                        <DoneIcon style={{cursor:'pointer'}} onClick={handleEditChannel}/>
                    </Stack>
                    }
                    {!isOwnChannel && <Typography variant='body2'>{channel?.name}</Typography>}
                    {!isOwnChannel && <Button variant='contained' style={{width: '50%'}}>Subscribe</Button>}
                </Stack>
            </Stack>
            <Divider/>
            <Videos/>
            {errorVal && <ErrorSnackbar/>}
        </div>
        </div>

  )
}

type Props = {}

const Videos = (props: Props) => {
  return (
    <div>Videos</div>
  )
}