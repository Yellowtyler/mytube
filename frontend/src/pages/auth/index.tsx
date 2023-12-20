import { FC, useState } from "react";
import { Box, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormLabel, Tab, Tabs, TextField } from "@mui/material";
import { useStore } from "@nanostores/react";
import { auth, closeAuthTab, createError, error, openAuth } from "../../stores/security";
import ErrorAlert from "../../ui/error-alert";
import { AuthApiImplService, RegisterRequest } from "../../api";
import ArrowBackIcon from '@mui/icons-material/ArrowBack'

export type AuthProps = {
    value: number;
    index: number;
};

export const Auth: FC = () => {
    
    let open = useStore(openAuth)
    const errorVal = useStore(error)

    const [value, setValue] = useState<number>(0);
    
    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
      setValue(newValue);
    };
    
    function switchTab(index: number) {
        return {
          id: `simple-tab-${index}`,
          'aria-controls': `simple-tabpanel-${index}`,
        };
    }

    return (
        <Dialog open={open} onClose={closeAuthTab}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab label="Login" {...switchTab(0)}/>
                    <Tab label="Register"  {...switchTab(1)}/>
                </Tabs>
            </Box>
            <Login index={0} value={value} />
            <Register index={1} value={value} />
           {errorVal && <ErrorAlert/>}
        </Dialog>
    ) 
}

const Login: FC<AuthProps> = (props: AuthProps) => {
    const [name, setName] = useState<string>('')
    const [password, setPassword] = useState<string>('')
    const [forgot, setForgot] = useState<boolean>(false)


    const login = () => {
        let req = {name: name, password: password}
        auth(req)
    }

    return (
        <div hidden={props.value !== props.index} id={`simple-tabpanel-${props.index}`} aria-labelledby={`simple-tab-${props.index}`}>
            {
            !forgot ? <div>
            <DialogContent>
                <TextField
                    value={name}
                    onChange={(e: any) => setName(e.target.value)}
                    autoFocus
                    margin="dense"
                    id="name"
                    label="Username"
                    type="text"
                    fullWidth
                    variant="standard"
                />
                <TextField
                    value={password}
                    onChange={(e: any) => setPassword(e.target.value)}
                    autoFocus
                    margin="dense"
                    id="password"
                    label="Password"
                    type="password"
                    fullWidth
                    variant="standard"
                />
                <Box sx={{ m: 3}}/>
                <FormLabel style={{cursor: 'pointer'}} onClick={(e: any) => setForgot(true)}>Forget password?</FormLabel>
            </DialogContent>
            <DialogActions>
                <Button onClick={closeAuthTab}>Cancel</Button>
                <Button onClick={login}>Login</Button>
            </DialogActions>
            </div>
            : <ForgotPassword setForgot={setForgot}/>
            }
       </div>
    )
}

type ForgotPasswordProps = {
    setForgot: (val: boolean) => void
}

const ForgotPassword: FC<ForgotPasswordProps> = (props: ForgotPasswordProps) => {

    const [mail, setMail] = useState<string>('') 
    const [show, setShow] = useState<boolean>(false)

    const forgotPassword = () => {
        let req = {mail: mail}
        AuthApiImplService.forgotPassword(req).then(r => setShow(true))
        .catch(e => {
            createError(e)
        })
    }

    return (
    <Box>
    <ArrowBackIcon sx={{marginLeft: 1, marginTop: 1, color: '#e53935'}} style={{cursor: 'pointer'}} onClick={(e: any) => props.setForgot(false)}/>
        <DialogContent>
            <TextField
                value={mail}
                onChange={(e: any) => setMail(e.target.value)}
                autoFocus
                margin="dense"
                id="mail"
                label="Enter your mail"
                type="mail"
                fullWidth
                variant="standard"
            />
            <Box sx={{ m: 3}}/>
        </DialogContent>
        <DialogActions>
            <Button onClick={closeAuthTab}>Cancel</Button>
            <Button onClick={forgotPassword}>Send mail request</Button>
        </DialogActions>
       <Dialog
                open={show}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                  {"Check your email"}
                </DialogTitle>
                <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    {`Please check your email ${mail} and go via link to reset password`}
                </DialogContentText>
                </DialogContent>
                <DialogActions>
                <Button onClick={(e: any) => {setShow(false); closeAuthTab()}}>Ok</Button>
                </DialogActions>
            </Dialog>

    </Box>
  )
}

const Register: FC<AuthProps> = (props: AuthProps) => {

    const [info, setInfo] = useState<RegisterRequest>({name: '', mail: '', password: ''})
    const [show, setShow] = useState<boolean>(false)

    const register = () => {
        AuthApiImplService.register(info)
        .then(r => setShow(true))
        .catch(e => createError(e))
    }

    return (
        <div hidden={props.value !== props.index} id={`simple-tabpanel-${props.index}`} aria-labelledby={`simple-tab-${props.index}`}>
            <DialogContent>
                <TextField
                    value={info.name}
                    onChange={(e: any) => setInfo(prev=>({...prev, name: e.target.value}))}
                    autoFocus
                    margin="dense"
                    id="name"
                    label="Username"
                    type="text"
                    fullWidth
                    variant="standard"
                />
                <TextField
                    value={info.mail}
                    onChange={(e: any) => setInfo(prev=>({...prev, mail: e.target.value}))}
                    autoFocus
                    margin="dense"
                    id="mail"
                    label="Mail"
                    type="mail"
                    fullWidth
                    variant="standard"
                />
                <TextField
                    value={info.password}
                    onChange={(e: any) => setInfo(prev=>({...prev, password: e.target.value}))}
                    autoFocus
                    margin="dense"
                    id="password"
                    label="Password"
                    type="text"
                    fullWidth
                    variant="standard"
                />
                </DialogContent>
            <DialogActions>
                <Button onClick={closeAuthTab}>Cancel</Button>
                <Button onClick={register}>Register</Button>
            </DialogActions>
            <Dialog
                open={show}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                  {"Check your email"}
                </DialogTitle>
                <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    {`Please check your email ${info.mail} and go via link to confirm registration`}
                </DialogContentText>
                </DialogContent>
                <DialogActions>
                <Button onClick={(e: any) => setShow(false)}>Ok</Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}
