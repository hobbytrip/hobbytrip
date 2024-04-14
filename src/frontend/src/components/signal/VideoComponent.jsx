import useSignalDeviceStore from "../../../actions/sessionState";

const { camera, setCamera, mice, setMice } = useSignalDeviceStore(store => store);
let stream;

async function getStream(){
    stream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true
    })
}

useEffect(() => {
    getStream();
}, [])

const CameraBtn = () => {
    const onClick = () => {
        setCamera(!camera);
        stream.getVideoTracks().forEach(track => {
            track.enabled = !camera;
        });
    }
    return(
        <>
        { camera ? (
            <button onClick={onClick}> Camera Off </button>
        ) : (
            <button onClick={onClick}> Camera On </button>
        )}
        </>
    )
}

const MiceBtn = () => {
    const onClick = () => {
        setMice(!mice);
        stream.getAudioTracks().forEach(track => {
            track.enabled = !mice;
        });
    }
    return(
        <>
        { mice ? (
            <button onClick={onClick}> Mice Off </button>
        ) : (
            <button onClick={onClick}> Mice On </button>
        )}
        </>
    )
}
    
const VideoComponent = () =>{
    return(
        <>
        <openvidu-webcomponent style="display: none"/> 
        <video autoPlay />
        <div>
            <CameraBtn />
            <MiceBtn />
        </div>
        </>
    )
}

export default VideoComponent;