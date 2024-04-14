import { Openvidu } from 'openvidu-browser';
import { useMutation, useMutation } from 'react-query';
import  axios  from 'axios';

const SERVER_URL = '';
let OV, session;

const joinSession = (props) =>{
    OV = new Openvidu();
    session = OV.initSession();

    session.on('sessionCreated', function(){
        const url = SERVER_URL + '/openvidu/api/sessions';
        const data = {
            Headers: {
                'Authorization': 'Basic EncodeBase64', // + EncodeBase64(OPENVIDUAPP:<YOUR_SECRET>)
                'Content-Type': 'application/json'
            },
            Body: {
                "mediaMode": "ROUTED",
                "object": "session",
                "forcedVideoCodec": "VP8",
                "allowTranscoding": false,
                "defaultRecordingProperties": {
                    "name": props.sessionName,
                    "hasAudio": true,
                    "hasVideo": true,
                    "outputMode": "COMPOSED",
                    "recordingLayout": "BEST_FIT",
                    "resolution": "1280x720",
                    "frameRate": 25,
                    "shmSize": 536870912,
                    "mediaNode": {
                        "id": "media_i-0c58bcdd26l11d0sd"
                    }
                },
                "mediaNode": {
                    "id": "media_i-0c58bcdd26l11d0sd"
                }
            }
        }
        const postSession = useMutation(() => 
        axios.post(url, data)
        .then());
    });
}

const leaveSession = () => {
    session.disconnect();
}


window.onbeforeunload = function(){
    if(session) session.disconnect();
};

export default { joinSession, leaveSession };