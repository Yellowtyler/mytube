import { Button } from '@mui/material'

type Props = {}

export const Home = (props: Props) => {
  return (
    <div>
      <Button onClick={(e:any)=>console.log('clicked')}>click</Button>
    </div>
  )
}