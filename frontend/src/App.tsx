import './App.css';
import { Route, Routes } from 'react-router-dom';
import { Layout } from './ui/layout';
import { NoMatch } from './pages/nomatch';
import { Home } from './pages/home';
import { ThemeProvider } from '@emotion/react';
import { createTheme } from '@mui/material';
import ResetPassword from './pages/reset-password';
import { Settings } from './pages/settings';
import { ErrorPage } from './pages/error-page';
import { Channel } from './pages/channel';
import { UploadVideo } from './pages/upload-video';
import { Video } from './pages/video';
import { ConfirmRegistration } from './pages/confirm-registration';

function App() {
  
  const theme = createTheme(
    {
      palette: {
        primary: {
          main: '#e53935',
        },
        secondary: {
          main: '#e64a19',
        },
      },
    }
  )

  return (
  <ThemeProvider theme={theme}>
    <Routes>
      <Route path='/' element={<Layout />}>
        <Route index element={<Home />} />
        <Route path='/reset-password/*' element={<ResetPassword/>}/>
        <Route path='/confirm-registration/*' element={<ConfirmRegistration/>}/>
        <Route path='/settings' element={<Settings/>} />
        <Route path='/error' element={<ErrorPage/>} />
        <Route path='/channel/*' element={<Channel/>} />
        <Route path='/upload-video' element={<UploadVideo/>}/>
        <Route path='/video/*' element={<Video/>}/>
        {/* Using path="*"" means "match anything", so this route
              acts like a catch-all for URLs that we don't have explicit
              routes for. */}
        <Route path="*" element={<NoMatch />} />
      </Route>
    </Routes>
  </ThemeProvider>
    )

}

export default App;
