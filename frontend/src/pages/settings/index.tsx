
import { Stack, Typography } from '@mui/material'
import React, { FC } from 'react'
import EditIcon from '@mui/icons-material/Edit';

type Props = {}

export const Settings: FC<Props> = (props: Props) => {
  return (
    <Stack alignContent={'center'} alignItems={'center'} padding={'1rem'} flexGrow={2}>
        <Typography fontSize={'h3'}>Account</Typography>
        <Stack direction={'row'} alignItems={'center'} alignContent={'center'} justifyContent={'center'}>
            <img src={require('../../icons/mytube.png')} style={{maxWidth: "10%"}}>
            </img>
            <span>name</span>
            <EditIcon/>
        </Stack>
        <Stack direction={'row'}> 
        <Typography>mail</Typography>
            <EditIcon/>
        </Stack>
    </Stack>
  )
}