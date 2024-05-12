import { FC, useEffect, useState } from 'react'
import { ChannelApiImplService, CommentDto, LikeApiImplService, LikeVideoRequest, OpenAPI, VideoApiImplService, VideoDto } from '../../api';
import { currentUser, fetchUserAndOtherData } from '../../stores/current-user';
import { useStore } from '@nanostores/react';
import { createError, error, token } from '../../stores/security';
import styles from './index.module.css';
import { Button, Paper, Stack, Typography } from '@mui/material';
import { getVideo } from '../../helpers/VideoApi';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import ThumbDownIcon from '@mui/icons-material/ThumbDown';
import ThumbUpOffAltIcon from '@mui/icons-material/ThumbUpOffAlt';
import ThumbDownOffAltIcon from '@mui/icons-material/ThumbDownOffAlt';
import { getImage } from '../../helpers/ImageApi';
import image from '../../icons/mytube.png'
import { parseDate } from '../../helpers/Date';
import { useNavigate } from 'react-router-dom';

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
            <Typography variant='h4' style={{marginTop: '1rem', marginLeft: '-47%'}}>{videoInfo?.name}</Typography>
            {videoInfo && <Buttons videoInfo={videoInfo!} userId={currUser?.id!}/>}
            {videoInfo && <Description views={videoInfo?.views!} uploadDate={parseDate(videoInfo?.createdAt!)} description={'tst'}/> }
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
    const [isOwner, setIsOwner] = useState<boolean>(false)
    const [isSubscribed, setIsSubscribred] = useState<boolean>(false)
    const navigate = useNavigate()

	useEffect(() => {
		setIsOwner(props.videoInfo.channel.isOwner)
        setIsSubscribred(props.videoInfo.channel.isSubscribed)
        getImage('profile', undefined,  props.videoInfo.channel.id)
		.then(r => {
			const data = r.data as Blob
			let newVal = data.size === 1 ? image : URL.createObjectURL(data)  
			setProfilePhoto(newVal)
		})
        .catch(e => createError(e))

	}, [])

    function goToChannelInfo() {
        navigate('/channel/' + props.videoInfo.channel.id)
    }

    function subscribe() {
        let req = {channelId: props.videoInfo.channel.id}
        ChannelApiImplService.subscribe(req)
            .then(r => {
                setIsSubscribred(true)
            })
            .catch(e => createError(e))
    }

    return (
            <div style={{display: 'flex', marginRight: '13rem', marginTop: '1rem', marginBottom: '1rem',justifyContent: 'space-evenly'}}>
                <img className={styles['image']} src={profilePhoto} onClick={(e:any)=>goToChannelInfo()}/>
                <div>
                    <Typography variant='h6' style={{cursor:'pointer'}} onClick={(e:any)=>goToChannelInfo()}>
                        {props.videoInfo.channel.name}</Typography>
                    <Typography variant='body2'>{props.videoInfo.channel.subscribersCount} subscribers</Typography>
                </div>

                {!isOwner && <Button variant='contained' disabled={isSubscribed} 
                    style={{height:'40px', marginTop: "1rem"}}
                    onClick={(e:any) => subscribe()}>
                        {isSubscribed ? 'Subscribed' : 'Subscribe'}
                </Button> }

                <LikeButtons videoInfo={props.videoInfo} userId={props.userId}/>
  
            </div>
    )
}


type LikeProps = {
    videoInfo: VideoDto;
    userId: string;
}

const LikeButtons: FC<LikeProps> = (props: LikeProps) => {
    
    const [like, setLike] = useState<boolean | null>()
    const [likeCount, setLikeCount] = useState<number>(0)
    const [dislikeCount, setDislikeCount] = useState<number>(0)

    useEffect(() => {
        setLike(props.videoInfo.isLike)
        setLikeCount(props.videoInfo.likesCount)
        setDislikeCount(props.videoInfo.dislikesCount)
    }, [])

    function likeOrDislikeVideo(newLikeVal: boolean) {
        if (like === null || like !== newLikeVal) {
            let req = {videoId: props.videoInfo.id, isLike: newLikeVal, userId: props.userId}
            LikeApiImplService.likeVideo(req)
                .then(r => {
                    setLike(newLikeVal)
                    if (newLikeVal && like == null) {
                        setLikeCount(prev=>prev+1)
                    } else if (!newLikeVal && like == null) {
                        setDislikeCount(prev=>prev+1)
                    } else if (newLikeVal && like !== newLikeVal) {
                        setLikeCount(prev=>prev+1)
                        setDislikeCount(prev=>prev-1)
                    } else {
                        setLikeCount(prev=>prev-1)
                        setDislikeCount(prev=>prev+1)
                    }
                })
                .catch(e => createError(e))
            return
        }

        LikeApiImplService.deleteLike(props.videoInfo.id)
            .then(r=>{
                setLike(null)
                if (newLikeVal) {
                    setLikeCount(prev=>prev-1)
                } else {
                    setDislikeCount(prev=>prev-1)

                }
            })
            .catch(e=>createError(e))

    }

    let likeButton;
    let dislikeButton;
    if (like === null) {
        likeButton = <ThumbUpOffAltIcon style={{cursor:'pointer'}} onClick={(e:any)=>likeOrDislikeVideo(true)}/>
        dislikeButton = <ThumbDownOffAltIcon style={{cursor:'pointer'}} onClick={(e:any)=>likeOrDislikeVideo(false)}/>

    } else if (like === true) {
        likeButton = <ThumbUpIcon style={{cursor:'pointer'}} onClick={(e:any)=>likeOrDislikeVideo(true)}/>
        dislikeButton = <ThumbDownOffAltIcon style={{cursor:'pointer'}} onClick={(e:any)=>likeOrDislikeVideo(false)}/>
    } else {
        likeButton = <ThumbUpOffAltIcon style={{cursor:'pointer'}} onClick={(e:any)=>likeOrDislikeVideo(true)}/>
        dislikeButton = <ThumbDownIcon style={{cursor:'pointer'}} onClick={(e:any)=>likeOrDislikeVideo(false)}/>
    }

    let elements = <Stack direction={'row'} spacing={2} justifyContent={'center'} alignItems={'center'}>
        {likeButton}
        <Typography>{likeCount}</Typography>
        {dislikeButton}
        <Typography>{dislikeCount}</Typography>
        </Stack>


    return elements
}

type DescriptionProps = {
    views: number;
    uploadDate: string;
    description: string;
}

const Description: FC<DescriptionProps> = (props: DescriptionProps) => {
  return (
    <Paper elevation={3} style={{width: '50%', padding:"1rem"}}> 
        {props.views} views {props.uploadDate} <br/>
        {props.description}
    </Paper>
  )
}

type CommentsProps = {}

const Comments = (props: CommentsProps) => {

    const [comments, setComments] = useState<CommentDto[]>([])

  return (
    <div>
        <AddComment/>
        {comments.map((i, v) => <Comment/>)}
    </div>
  )
}


type AddCommentProps = {}

const AddComment = (props: AddCommentProps) => {
  return (
    <div>AddComment</div>
  )
}

type CommentProps = {}

const Comment = (props: CommentProps) => {
  return (
    <div>Comment</div>
  )
}