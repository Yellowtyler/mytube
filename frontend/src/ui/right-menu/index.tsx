
import React, { FC } from 'react'
import Divider from '@mui/material/Divider';
import MenuItem from '@mui/material/MenuItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemIcon from '@mui/material/ListItemIcon';
import { Menu } from '@mui/material';
import SettingsIcon from '@mui/icons-material/Settings';
import { logout, openAuthTab, token } from "../../stores/security";
import LoginIcon from '@mui/icons-material/Login'
import LogoutIcon from '@mui/icons-material/Logout'
import { useStore } from "@nanostores/react";
import { Link, useNavigate } from 'react-router-dom';

type RightMenuProps = {
    anchor: HTMLElement | null;
    setAnchor: (v: HTMLElement | null) => void

}

export const RightMenu: FC<RightMenuProps> = (props: RightMenuProps) => {
    const tokenVal = useStore(token)
    const navigate = useNavigate()

    const open = Boolean(props.anchor)

    const handleClose = () => props.setAnchor(null)

  return (
     <Menu anchorEl={props.anchor} open={open} onClose={handleClose}>
        <MenuItem onClick={(e: any) => {navigate('/settings'); handleClose();}}>
          <ListItemIcon>
            <SettingsIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText>Settings</ListItemText>
       </MenuItem>
        <Divider />
           {!tokenVal.data ? 
            <MenuItem onClick={(e:any) => {openAuthTab(); handleClose();}}>
                <ListItemIcon> 
                <LoginIcon  />
                </ListItemIcon>
                <ListItemText>Login</ListItemText>    
            </MenuItem>
            :     
            <MenuItem onClick={(e:any) => {logout(); handleClose();}}>
                <ListItemIcon> 
                <LogoutIcon  />
                 </ListItemIcon>
                <ListItemText>Logout</ListItemText>    
            </MenuItem>
            }
      </Menu>
    )
}