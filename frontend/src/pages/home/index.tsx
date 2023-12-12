import { Button } from '@mui/material'
import { openAuthTab } from '../../stores/security'

type Props = {}

export const Home = (props: Props) => {
  return (
    <div>
      <Button onClick={openAuthTab}>click</Button>
    </div>
  )
}