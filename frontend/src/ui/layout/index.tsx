import { FC} from "react";

import styles from './index.module.css'
import { Stack } from "@mui/material";
import { Outlet } from "react-router-dom";
import { openAuthTab } from "../../stores/security";
import { Auth } from "../../pages/auth";


export const Layout: FC = () => {

    return (
        <div className={styles.layout}>
            <header className={styles.header}>
                <a href={'/'}>
                    <img className={styles.icon} src={require("../../icons/mytube.png")}></img>
                </a>
                <Stack direction={'row'}>
                    <a onClick={(e: React.SyntheticEvent) => {openAuthTab()}}>Login</a>
                </Stack>
            </header>
            <Auth/>
            <Outlet/>
        </div>
    )
};