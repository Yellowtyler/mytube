import React, { FC } from 'react'
import { token } from '../../stores/security'
import { useStore } from '@nanostores/react'
import { UserShortDto } from '../../api'
import { currentUser } from '../../stores/current-user'
import Forbidden from '../../pages/forbidden'
import { Navigate, useLocation } from 'react-router-dom'

type ProtectedPageProps = {
    children: JSX.Element;
    roles: UserShortDto.role[];
}

export const ProtectedPage = (props: ProtectedPageProps) => {
    let tokenData = useStore(token)
    let currUser = useStore(currentUser)
    let location = useLocation()
    console.log('check')
    if (!tokenData.data) {
        console.log('navigato to auth')
        return <Navigate to='/auth' state={{from: location}} replace/>
    }
    
    if (currUser && !props.roles.includes(currUser.role)) {
      return <Forbidden></Forbidden> 
    }

    return props.children 
}