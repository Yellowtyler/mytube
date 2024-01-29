import { FC, useRef, useState } from 'react'
import styles from './index.module.css'
import { Button, LinearProgress, Stack, TextField, Typography} from '@mui/material'
import { uploadVideo } from '../../libs/VideoApi'
import { createError } from '../../stores/security'
import AddAPhotoIcon from '@mui/icons-material/AddAPhoto';
import { uploadImage } from '../../libs/ImageApi'
import { useStore } from '@nanostores/react'
import { currentUser } from '../../stores/current-user'
import { ErrorPage } from '../error-page'
import { Notification } from '../../ui/notification'

export const UploadVideo: FC = () => {

    const currUser = useStore(currentUser)

    const [title, setTitle] = useState<string>('')
    const [video, setVideo] = useState<any>()
    const [currVideo, setCurrVideo] = useState<any>()
    const [currPoster, setCurrPoster] = useState<any>()
    const [poster, setPoster] = useState<string>('')
    const [progress, setProgress] = useState<number>(0)
    const [loading, setLoading] = useState<boolean>(false)
    const [uploaded, setUploaded] = useState<boolean>(false)
    const hiddenInput = useRef<HTMLInputElement>(null)

    const handleUploadVideo = (event: any) => {
        const file = event.target.files[0]
        setCurrVideo(file)
        const fileReader = new FileReader();
        setLoading(true)
        fileReader.addEventListener('progress', (e: ProgressEvent<FileReader>) => {
            console.log(e.loaded/e.total)
            setProgress(Math.round((e.loaded/e.total)*100)) 
        })
        fileReader.addEventListener("load", () => {
            setVideo(fileReader.result)
        });
        fileReader.addEventListener('loadend', () => {
            setLoading(false)
        })
        fileReader.readAsDataURL(file);
    }
    
    const saveUploadedVideo = () => {
        uploadVideo(title, poster, currVideo)
        .then(r => setUploaded(true))
        .catch(e => createError(e))
    }

    const handleUploadImage = (event: any, type: string) => { 
        const file = event.target.files[0]
        uploadImage(type, currUser!.id, file)
        .then(r => setPoster(r.data.path))
        .catch(e => createError(e))
        const fileReader = new FileReader();
        fileReader.addEventListener("load", () => {
            setCurrPoster(fileReader.result)       
        });
        fileReader.readAsDataURL(file);
    }

    if (!currUser) {
        return <ErrorPage msg={'Login for uploading video'}/>
    }

    return (
        
        <div className={styles['box']}>
            <Stack spacing={2} width={'30%'} margin={'1rem'}>
                <TextField 
                    label='Title'
                    placeholder='Enter video title'
                    type='text'
                    value={title}
                    onChange={(e: any) => setTitle(e.target.value)}  
                />
                <div className={styles['profile-container']}>
                   {!currPoster && 
                    <Stack justifyContent={'center'} alignItems={'center'} spacing={4} padding={'1rem'} width={'500px'}
                        style={{backgroundColor: 'whitesmoke', borderRadius: '10%'}}>
                        <AddAPhotoIcon fontSize='large'/>
                        <Typography>(Optional) Upload video poster here</Typography>
                    </Stack>
                   }

                   {currPoster && <img className={styles['image']} src={currPoster}/>}
                    <div className={styles['overlay-profile']} onClick={() => hiddenInput.current?.click()}>
                        <AddAPhotoIcon fontSize='large'/>
                    </div> 
                    <input
                        ref={hiddenInput} 
                        type='file'
                        accept='image/*'
                        style={{display:'none'}}
                        onChange={(e: any) =>handleUploadImage(e, 'poster')}
                    />
                </div>
                <input
                    type='file'
                    accept='video/*'
                    onChange={handleUploadVideo}
                />
                {loading && <LinearProgress variant='determinate' value={progress}/>}
                {video && 
                    <video controls src={video} id='preupload'>
                    </video>
                }
                <Button variant='contained' disabled={uploaded} onClick={saveUploadedVideo}>Upload</Button> 
            </Stack>
            <Notification open={uploaded} setOpen={setUploaded} msg={'Video has been successfully uploaded!'}/>
        </div>
    )
}