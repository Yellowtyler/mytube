import HomeIcon from '@mui/icons-material/Home';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import FavoriteIcon from '@mui/icons-material/Favorite';
import SubscriptionsIcon from '@mui/icons-material/Subscriptions';
import VideoCallIcon from '@mui/icons-material/VideoCall';
import styles from './index.module.css'

export const SidebarData = [
    {
        title: 'Home',
        path: '/',
        icon: <HomeIcon />,
        cName: styles.nav_text
    },
    {
        title: 'Channel',
        path: '/channel',
        icon: <AccountBoxIcon/>,
        cName: styles.nav_text 
    },
    {
        title: 'Liked videos',
        path: '/liked',
        icon: <FavoriteIcon/>,
        cName: styles.nav_text 
    },
    {
        title: 'Subscriptions',
        path: '/subscriptions',
        icon: <SubscriptionsIcon/>,
        cName: styles.nav_text 
    },
    {
        title: 'Upload video',
        path: '/upload-video',
        icon: <VideoCallIcon/>,
        cName: styles.nav_text
    }
]