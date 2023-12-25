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
import { useEffect } from 'react';
import { useStore } from '@nanostores/react';
import { token } from './stores/security';
import { OpenAPI } from './api';

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

  const tokenVal = useStore(token)
  
  useEffect(() => {
      if (tokenVal.data) {
        OpenAPI.TOKEN = tokenVal.data
      }
  }, [])

  return (
  <ThemeProvider theme={theme}>
    <Routes>
    
      <Route path='/' element={<Layout />}>
        <Route index element={<Home />} />
        <Route path='/reset-password/*' element={<ResetPassword/>}/>
        <Route path='/settings' element={<Settings/>} />
        <Route path='/error' element={<ErrorPage/>} />
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
