import { FC, useRef, useState } from 'react'
import styles from './index.module.css'
import { Button, LinearProgress, Stack, TextField} from '@mui/material'
import { uploadVideo } from '../../libs/VideoApi'
import { createError } from '../../stores/security'
import AddAPhotoIcon from '@mui/icons-material/AddAPhoto';
import { uploadImage } from '../../libs/ImageApi'
import { useStore } from '@nanostores/react'
import { currentUser } from '../../stores/current-user'
import { ErrorPage } from '../error-page'

export const UploadVideo: FC = () => {

    const currUser = useStore(currentUser)

    const [title, setTitle] = useState<string>('')
    const [video, setVideo] = useState<any>()
    const [currFile, setCurrFile] = useState<any>()
    const [poster, setPoster] = useState<any>()
    const [progress, setProgress] = useState<number>(0)
    const [loading, setLoading] = useState<boolean>(false)
    const hiddenInput = useRef<HTMLInputElement>(null)

    const handleUploadVideo = (event: any) => {
        const file = event.target.files[0]
        setCurrFile(file)
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
            setPoster((document.getElementById('preupload') as HTMLVideoElement))
        })
        fileReader.readAsDataURL(file);
    }
    
    const saveUploadedVideo = () => {
        uploadVideo(title, currFile)
        .then(r => console.log(r))
        .catch(e => createError(e))
    }

    const handleUploadImage = (event: any, type: string) => { 
        const file = event.target.files[0]
        uploadImage(type, currUser!.id, file)
        const fileReader = new FileReader();
        fileReader.addEventListener("load", () => {
            setPoster(fileReader.result)       
        });
        fileReader.readAsDataURL(file);
    }

    if (!currUser) {
        return <ErrorPage msg={'Login for uploading video'}/>
    }

    return (
        
        <div className={styles['box']}>
            <Stack spacing={2} width={'30%'}>
                <TextField 
                    label='Title'
                    placeholder='Enter video title'
                    type='text'
                    value={title}
                    onChange={(e: any) => setTitle(e.target.value)}  
                />
                <div className={styles['profile-container']}>
                    <img className={styles['image']} src={poster}/>
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
                <Button variant='contained' onClick={saveUploadedVideo}>Upload</Button> 
            </Stack>
        </div>
    )
}