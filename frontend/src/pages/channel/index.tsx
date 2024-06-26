import { FC, useEffect, useRef, useState } from 'react'
import { ChannelApiImplService, ChannelDto, EditChannelRequest, OpenAPI, VideoShortDto } from '../../api'
import { createError, error, token } from '../../stores/security'
import { useStore } from '@nanostores/react'
import { currentUser, fetchUserAndOtherData} from '../../stores/current-user'
import { Button, Card, CardContent, CardMedia, Divider, Grid, Stack, TextField, Typography } from '@mui/material'
import { getImage, uploadImage } from '../../helpers/ImageApi'
import image from '../../icons/mytube.png'
import background from '../../icons/default_background.jpg'
import styles from './index.module.css'
import AddAPhotoIcon from '@mui/icons-material/AddAPhoto';
import EditIcon from '@mui/icons-material/Edit';
import { ErrorSnackbar } from '../../ui/error-snackbar'
import DoneIcon from '@mui/icons-material/Done';
import { getVideos } from '../../helpers/VideoApi'
import { parseDate } from '../../helpers/Date'
import { useNavigate } from 'react-router-dom'
import { WithLoadingScreen } from '../../ui/with-loading-screen'

export const Channel: FC = () => {
    
    const currUser = useStore(currentUser)
    const tokenVal = useStore(token)
    const errorVal = useStore(error)

    const [channel, setChannel] = useState<ChannelDto>()
    const [profilePhoto, setProfilePhoto] = useState<any>(image)
    const [backgroundPhoto, setBackgroundPhoto] = useState<any>(background)
    const [isOwnChannel, setIsOwnChannel] = useState<boolean>(false)
    const [editChannelReq, setEditChannelReq] = useState<EditChannelRequest>({id: '', name: '', description: ''})
    const [edit, setEdit] = useState<boolean>(false)
    const [isSubscribeDisabled, setIsSubscribeDisabled] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(true)

    const hiddenProfileInput = useRef<HTMLInputElement>(null)
    const hiddenBackgroundInput = useRef<HTMLInputElement>(null)

    useEffect(() => {
        OpenAPI.TOKEN = tokenVal!.data!  
        fetchUserAndOtherData(fetchChannelData);
    }, [channel])
    
    const fetchChannelData = () => {
        let id = window.location.pathname.split('/').pop();
        ChannelApiImplService.getChannel(id!)
                .then(chan => {

                    setChannel(chan)
                    setIsSubscribeDisabled(chan.isSubscribed)
                    if (currUser && chan.id === currUser?.channelId!) {
                        setIsOwnChannel(true)
                        setEditChannelReq({id: chan.id, name: chan.name, description: chan.description})
                    }
                
                    getImage('profile', undefined, chan.id).then(r => {
                        const data = r.data as Blob
                        let newVal = data.size === 1 ? image : URL.createObjectURL(data)  
                        setProfilePhoto(newVal)

                        getImage('background', undefined, chan.id).then(r => {
                            const data = r.data as Blob
                            let newVal = data.size === 1 ? background : URL.createObjectURL(data)  
                            setBackgroundPhoto(newVal)
                            setLoading(false)
                        })
                    })

                    
                })
                .catch(e => createError(e))

    }

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

    const subscribe = () => {
        let req = {channelId: channel?.id!}
        ChannelApiImplService.subscribe(req).then(r => {
            setIsSubscribeDisabled(true)
        })
    }

    
    const channelInfo = (
            <div>
                <div className={styles['background-container']}>
                    <img className={styles['background']} src={backgroundPhoto}/>
                    {isOwnChannel && <div className={styles['overlay-background']} onClick={openBackgroundPhotoDialog}>
                    <AddAPhotoIcon fontSize='large'/>
                    </div>}
                </div>
                
                <Stack direction={'row'} marginBottom={'1rem'} width={'700px'}>
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
                        {!isOwnChannel && tokenVal.data && <Button variant='contained' style={{width: '50%'}} disabled={isSubscribeDisabled} onClick={subscribe}>
                            {isSubscribeDisabled ? 'Subscribed' : 'Subscribe'}
                            </Button>}
                        
                    </Stack>
                </Stack>
                <Divider/>
                <Videos channel={channel}/>
                {errorVal && <ErrorSnackbar/>}
            </div>
    )

    return (
        <div className={styles['box']}>
            <WithLoadingScreen loading={loading} element={channelInfo} />
        </div>
  )
}

type VideosProps = {
    channel: ChannelDto | undefined;
}

const Videos: FC<VideosProps> = (props: VideosProps) => {

    const [videos, setVideos] = useState<VideoShortDto[]>([])
    const [page, setPage] = useState<number>(0)
    const [isFetch, setIsFetch] = useState<boolean>(true) 
    const [totalPages, setTotalPages] = useState<number>(0)

    const size = 3;
   
    useEffect(() => {
        if (!isFetch || (page !== 0 && page === totalPages)) return

        window.addEventListener('scroll', handleScroll)
        
        let id = window.location.pathname.split('/').pop();
        let req = {channelId: id, page: page, size: size}
        
        getVideos(req)
        .then(r => {
            const prevVideos = videos
            const newVideos: VideoShortDto[] = [...prevVideos, ...r.data.list]
            newVideos.forEach((v) => {
                v.createdAt = parseDate(v.createdAt)
            })
            setVideos(newVideos)
            setIsFetch(false)
            setPage(page+1)
            setTotalPages(r.data.totalPages)
        })
        .catch(e => createError(e))

    }, [isFetch, props.channel])

    const handleScroll = () => {
        if (window.innerHeight + document.documentElement.scrollTop !== document.documentElement.offsetHeight) return;
        setIsFetch(true)
    }

    return (
        <div>
            <Grid container rowSpacing={1} columnSpacing={{ xs: 2, sm: 3, md: 4 }} marginTop={'1rem'} >
                {videos.map((v, index) => {
                    return (
                        <Video video={v}/>
                    )
                })
                }
            </Grid>
        </div>
    )
}

type VideoProps = {
    video: VideoShortDto 
}

const Video: FC<VideoProps> = (props: VideoProps) => {

    const [poster, setPoster] = useState<any>()
    const navigate = useNavigate()

    useEffect(() => {
        getImage('poster', undefined, undefined, props.video.id)
        .then(r => {
            const data = r.data as Blob
            let newVal = data.size === 0 ? image : URL.createObjectURL(data)  
            setPoster(newVal)
        })
        .catch(e => createError(e))
    },[])

    return (
         <Grid item xs={4} padding={'1rem'}>
            <Card style={{padding:'0.5rem', cursor: 'pointer'}} onClick={(e: any) => navigate('/video/' + props.video.id)}>
                <CardMedia
                    sx={{ height: 140 }}
                    image={poster}
                    title={props.video.name}
                />
                <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {props.video.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                   {props.video.views +" ‧ " + props.video.createdAt} 
                </Typography>
                </CardContent>
        </Card>
        </Grid>
    )
}