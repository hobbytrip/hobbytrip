import Btn from '../../components/Signal/ConnectionBtn/ConnectionBtn';
import videoBtn from '../../components/Signal/VideoComponent';
import useMediaConnectedStore from '../../actions/mediaState';
import VideoComponent from '../../components/Signal/VideoComponent';

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