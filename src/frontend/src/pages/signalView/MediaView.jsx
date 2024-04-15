import Btn from '../../components/signal/ConnectionBtn';
import VideoComponent from '../../components/signal/VideoComponent';

const MediaView = () => {
    return(
        <>
        <Btn.StartBtn />
        <Btn.EndBtn />
        <VideoComponent />
        </>
    )
}

export default MediaView;