
import React, { FC } from 'react'
import CloseIcon from '@mui/icons-material/Close';
import { SidebarData } from './sidebarData';
import { Link } from 'react-router-dom';
import styles from './index.module.css'

type LeftMenuProps = {
    sidebar: boolean;
    setSidebar: (v: boolean) => void;
}

export const LeftMenu: FC<LeftMenuProps> = (props: LeftMenuProps) => {


    return (
      <nav className={props.sidebar ? styles.nav_menu : styles.nav_menu + ".active"}>
        <ul className={styles.nav_menu_items}>
          <li style={{listStyle: 'none'}}>
            <CloseIcon style={{color: '#f5f5f5', marginLeft: '2px', cursor: 'pointer'}} fontSize='large' onClick={(e: any)=> props.setSidebar(false)}/>
          </li>
          {SidebarData.map((item, index) => {
              return <li key={index} className={item.cName}>
                  <Link to={item.path} onClick={(e: any)=>props.setSidebar(false)}>
                    {item.icon}
                    <span>{item.title}</span>
                  </Link>
              </li>
          })}
        </ul>
      </nav>
    );
}
