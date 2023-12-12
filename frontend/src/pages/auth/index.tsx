import { FC, useState } from "react";
import { Box, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Tab, Tabs, TextField } from "@mui/material";
import { useStore } from "@nanostores/react";
import { auth, closeAuthTab, openAuth } from "../../stores/security";
import ErrorAlert from "../../ui/error-alert";
import { AuthApiImplService, RegisterRequest } from "../../api";

export type AuthProps = {
    value: number;
    index: number;
};

export const Auth: FC = () => {
    
    let open = useStore(openAuth)

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
        </Dialog>
    ) 
}

export const Login: FC<AuthProps> = (props: AuthProps) => {
    const [name, setName] = useState<string>('')
    const [password, setPassword] = useState<string>('')
    const [isError, setIsError] = useState<boolean>(false)

    const login = () => {
        let req = {name: name, password: password}
        try {
            auth(req)
        } catch (e) {
            setIsError(true)
            return
        }
        closeAuthTab()
        setIsError(false)
    }

    return (
        <div hidden={props.value !== props.index} id={`simple-tabpanel-${props.index}`} aria-labelledby={`simple-tab-${props.index}`}>
            <DialogTitle>Login</DialogTitle>
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
            </DialogContent>
            <DialogActions>
                <Button onClick={closeAuthTab}>Cancel</Button>
                <Button onClick={login}>Login</Button>
            </DialogActions>
            {isError && <ErrorAlert/>}
        </div>
    )
}

export const Register: FC<AuthProps> = (props: AuthProps) => {

    const [info, setInfo] = useState<RegisterRequest>({name: '', mail: '', password: ''})
    const [show, setShow] = useState<boolean>(false)

    const register = () => {
        AuthApiImplService.register(info).then(r => setShow(true))
        .catch(e => console.log(e))
    }

    return (
        <div hidden={props.value !== props.index} id={`simple-tabpanel-${props.index}`} aria-labelledby={`simple-tab-${props.index}`}>
            <DialogTitle>Register</DialogTitle>
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
                onClose={(e:any)=>setShow(false)}
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
