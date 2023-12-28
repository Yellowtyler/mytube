
import React, { FC } from 'react'
import CloseIcon from '@mui/icons-material/Close';
import { SidebarData } from './sidebarData';
import { Link } from 'react-router-dom';
import styles from './index.module.css'
import { useStore } from '@nanostores/react';
import { currentUser } from '../../stores/current-user';
import { token } from '../../stores/security';

type LeftMenuProps = {
    sidebar: boolean;
    setSidebar: (v: boolean) => void;
}

export const LeftMenu: FC<LeftMenuProps> = (props: LeftMenuProps) => {
    
    const currUser = useStore(currentUser)
    const tokenVal = useStore(token)

    return (
      <nav className={props.sidebar ? styles.nav_menu : styles.nav_menu + ".active"}>
        <ul className={styles.nav_menu_items}>
          <li style={{listStyle: 'none'}}>
            <CloseIcon style={{color: '#f5f5f5', marginLeft: '2px', cursor: 'pointer'}} fontSize='large' onClick={(e: any)=> props.setSidebar(false)}/>
          </li>
          {SidebarData.map((item, index) => {
              let path = item.path
              if (item.title === 'Channel') {
                  path += `/${currUser?.channelId}`
              }

              if (!tokenVal.data && item.title !== 'Home') {
                  return 
              }

              return <li key={index} className={item.cName}>
                  <Link to={path} onClick={(e: any)=>props.setSidebar(false)}>
                    {item.icon}
                    <span>{item.title}</span>
                  </Link>
              </li>
          })}
        </ul>
      </nav>
    );
}
