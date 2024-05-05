import { CircularProgress } from '@mui/material';

type Props = {
    loading: boolean;
    element: JSX.Element
}

export const WithLoadingScreen = (props: Props) => {
  return (
    <div>
        {props.loading ? <CircularProgress/> : props.element}
    </div>
  )
}