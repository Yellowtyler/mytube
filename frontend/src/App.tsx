import './App.css';
import { Route, Routes } from 'react-router-dom';
import { Layout } from './ui/layout';
import { NoMatch } from './pages/nomatch';
import { Home } from './pages/home';
import { ThemeProvider } from '@emotion/react';
import { createTheme } from '@mui/material';

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
