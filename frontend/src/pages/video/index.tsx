import { FC, useEffect, useState } from 'react'
import { OpenAPI, VideoApiImplService, VideoDto } from '../../api';
import { currentUser, fetchUserAndOtherData } from '../../stores/current-user';
import { useStore } from '@nanostores/react';
import { createError, error, token } from '../../stores/security';
import styles from './index.module.css';
import { Button, Stack, Typography } from '@mui/material';
import { getVideo } from '../../libs/VideoApi';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import ThumbDownIcon from '@mui/icons-material/ThumbDown';
import { getImage } from '../../libs/ImageApi';
import image from '../../icons/mytube.png'

export const Video: FC = () => {

    const currUser = useStore(currentUser)
    const tokenVal = useStore(token)
    const errorVal = useStore(error)

    const [videoInfo, setVideoInfo] = useState<VideoDto>()
    const [video, setVideo] = useState<any>()

    useEffect(() => {
        OpenAPI.TOKEN = tokenVal!.data!  
        fetchUserAndOtherData(fetchVideoData);
    }, [])
    
    function fetchVideoData() {
        let id = window.location.pathname.split('/').pop();
        VideoApiImplService.getVideoInfo(id!).then(r => {
            setVideoInfo(r)
            getVideo(id!).then(r => {
                const url = URL.createObjectURL(new Blob([r.data], {type: "video/mp4"}));
                setVideo(url)
            })
        })
        .catch(e => createError(e))
    }
    
    return (
        <div className={styles['container']}>
            {video && <video className={styles['video']} src={video} controls autoPlay loop>
            </video>}
            <Typography variant='h4'>{videoInfo?.name}</Typography>
            {videoInfo && <Buttons videoInfo={videoInfo!} userId={currUser?.id!}/>}
            <Description/>
            <Comments/>
        </div>
    )
}

type ButtosProps = {
    videoInfo: VideoDto;
    userId: string;
}

const Buttons = (props: ButtosProps) => {

    const [profilePhoto, setProfilePhoto] = useState<any>()
	
	useEffect(() => {
		getImage('profile', props.videoInfo.channel.id)
		.then(r => {
			const data = r.data as Blob
			let newVal = data.size === 0 ? image : URL.createObjectURL(data)  
			setProfilePhoto(newVal)
		})
        .catch(e => createError(e))

	}, [])

    return (
        <Stack direction={'row'} spacing={8}>
       
        <Stack direction={'row'}>
			<img className={styles['image']} src={profilePhoto}/>
			<Stack direction={'column'}>
				<Typography>{props.videoInfo.channel.name}</Typography>
				<Typography variant='body2'>{props.videoInfo.channel.subscribersCount}</Typography>
			</Stack>
        </Stack>
      
        <Button variant='contained'>Subscribe</Button>
        <Stack direction={'row'} spacing={2}>
			<ThumbUpIcon/>
			<Typography>{props.videoInfo.likesCount}</Typography>
			<ThumbDownIcon/>
			<Typography>{props.videoInfo.dislikesCount}</Typography>
        </Stack>

        </Stack>
    )
}

type DescriptionProps = {}

const Description = (props: DescriptionProps) => {
  return (
    <div>Description</div>
  )
}

type CommentsProps = {}

const Comments = (props: CommentsProps) => {
  return (
    <div>Comments</div>
  )
}