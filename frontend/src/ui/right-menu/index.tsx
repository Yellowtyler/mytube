
import React, { FC } from 'react'
import Divider from '@mui/material/Divider';
import MenuItem from '@mui/material/MenuItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemIcon from '@mui/material/ListItemIcon';
import { Menu } from '@mui/material';
import SettingsIcon from '@mui/icons-material/Settings';
import { logout } from "../../stores/security";
import LogoutIcon from '@mui/icons-material/Logout'
import { useNavigate } from 'react-router-dom';

type RightMenuProps = {
    anchor: HTMLElement | null;
    setAnchor: (v: HTMLElement | null) => void

}

export const RightMenu: FC<RightMenuProps> = (props: RightMenuProps) => {
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
           <MenuItem onClick={(e:any) => {
                  logout(); 
                  navigate('/');
                  handleClose();
              }}
            >
                <ListItemIcon> 
                <LogoutIcon  />
                 </ListItemIcon>
                <ListItemText>Logout</ListItemText>    
            </MenuItem>
      </Menu>
  )
}