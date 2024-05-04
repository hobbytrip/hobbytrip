import Btn from '../../components/signal/ConnectionBtn/ConnectionBtn';
import videoBtn from '../../components/signal/VideoComponent';
import useMediaConnectedStore from '../../actions/mediaState';
import VideoComponent from '../../components/signal/VideoComponent';

const MediaView = () => {
    const { mediaConnected } = useMediaConnectedStore(state => state);
    return(
        <>
        <div>
                        
        </div>
        <div>
            { mediaConnected ? (
                <>
                <videoBtn.MiceBtn />
                <videoBtn.CameraBtn />
                <Btn.EndBtn />
                </>
            ) : (
                <Btn.StartBtn />
            )}
        </div>
        </>
    )
}

export default MediaView;