import { FC, useState} from "react";

import styles from './index.module.css'
import { Stack} from "@mui/material";
import { Outlet } from "react-router-dom";
import { Auth } from "../../pages/auth";
import MenuIcon from '@mui/icons-material/Menu';
import { LeftMenu } from "../left-menu";
import AccountCircleIcon from '@mui/icons-material/AccountCircle'
import { RightMenu } from "../right-menu";
import { useStore } from "@nanostores/react";
import { openAuthTab, token } from "../../stores/security";
import LoginIcon from '@mui/icons-material/Login'

export const Layout: FC = () => {
    
    const [sidebar, setSidebar] = useState<boolean>(false)
    const showSidebar = () => setSidebar(!sidebar)
    const [rightAnchor, setRightAnchor] = useState<HTMLElement | null>(null)
    const tokenVal = useStore(token)

    const openRightMenu = (e: any) => {
        setRightAnchor(e.currentTarget)
    } 

    return (
        <div className={styles.layout}>
            <header className={styles.header}>
                <MenuIcon onClick={showSidebar} sx={{marginTop: 1, marginRight: 1, marginLeft: 1, color: '#f5f5f5'}} fontSize="large" style={{cursor: 'pointer'}}/> 
                <Stack direction={'row'}>
                {
                    tokenVal.data ? 
                    <AccountCircleIcon onClick={openRightMenu} fontSize="large" sx={{marginRight: 1, padding: 1, color: '#f5f5f5'}} style={{cursor: 'pointer'}} />
                    :
                    <LoginIcon onClick={openAuthTab} fontSize="large" sx={{marginRight: 1, padding: 1, color: '#f5f5f5'}} style={{cursor: 'pointer'}}/>
                }
                </Stack>
            </header>
            <Auth/>
            {sidebar &&<LeftMenu sidebar={sidebar} setSidebar={setSidebar}/>}
           <RightMenu anchor={rightAnchor} setAnchor={setRightAnchor} />
            <Outlet/>
        </div>
    )
};