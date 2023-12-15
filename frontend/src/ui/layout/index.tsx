import { FC} from "react";

import styles from './index.module.css'
import { Stack } from "@mui/material";
import { Outlet } from "react-router-dom";
import { logout, openAuthTab, token } from "../../stores/security";
import { Auth } from "../../pages/auth";
import LoginIcon from '@mui/icons-material/Login'
import LogoutIcon from '@mui/icons-material/Logout'
import { useStore } from "@nanostores/react";

export const Layout: FC = () => {
    
    const tokenVal = useStore(token)

    return (
        <div className={styles.layout}>
            <header className={styles.header}>
                <a href={'/'}>
                    <img className={styles.icon} src={require("../../icons/mytube.png")}></img>
                </a>
                <Stack direction={'row'}>
                    {!tokenVal.data && 
                        <LoginIcon sx={{marginTop: 3, marginRight: 1}} fontSize="large" style={{cursor: 'pointer'}} onClick={openAuthTab} />
                    }
                    {tokenVal.data && 
                        <LogoutIcon sx={{marginTop: 3, marginRight: 1}} fontSize="large" style={{cursor: 'pointer'}} onClick={logout} />
                    }
                </Stack>
            </header>
            <Auth/>
            <Outlet/>
        </div>
    )
};