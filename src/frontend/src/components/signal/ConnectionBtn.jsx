import { useQueryClient } from 'react-query';
import sessionFunc from '../../hooks/mediaSession';
import { useState, useEffect } from 'react';
import axios from 'axios';

const [session, setSession] = useState({});
const SERVER_URL = '';

// 기존 session이 있는지 가져오기
useEffect(() => {
    axios.get(`${SERVER_URL}/openvidu/api/sessions/${serverId}/${channelId}`)
    .then(res => setSession(res))
    .catch(err => alert(err));
}, []);


const StartBtn = () => {
    function onClick(){
        // 서버, 채널 정보 요청하기
        if(session.id === ''){
            // 세션이 존재하지 않으므로 생성하기            
            const url = SERVER_URL + '/openvidu/api/sessions';
            const data = {
                header: {
                    'Authorization': 'Basic EncodeBase64', // + EncodeBase64(OPENVIDUAPP:<YOUR_SECRET>), 토큰?
                    'Content-Type': 'application/json'
                },
                body: {
                    "mediaMode": "ROUTED",
                    "object": "session",
                    "customSessionId": `${serverId}/${channelId}`,
                    "forcedVideoCodec": "VP8",
                    "allowTranscoding": false,
                    "defaultRecordingProperties": {
                        "name": `${channelId} videocall`,
                        "hasAudio": true,
                        "hasVideo": true,
                        "outputMode": "COMPOSED",
                        "recordingLayout": "BEST_FIT",
                        "resolution": "1280x720",
                        "frameRate": 25,
                        "shmSize": 536870912,
                        "mediaNode": {
                            "id": `${channelId}/${userId}`
                        }
                    }
                }
            }
            sessionFunc.createSession(url, data);   // session 생성
        }

        // 세션이 없으면 생성 후 접속, 있으면 바로 접속
        const url = SERVER_URL + `/openvidu/api/sessions/${session.sessionId}/connection`;
        const data = {
            header: {
                'Authorization': 'Basic EncodeBase64', // + EncodeBase64(OPENVIDUAPP:<YOUR_SECRET>), 토큰?
                'Content-Type': 'application/json' // 세션 만드는 거라고 보내줘야 하나?    
            },
            body: {
                "type": "WEBRTC",
                "data": "My Server Data",
                "role": "PUBLISHER",
                "kurentoOptions": {
                    "videoMaxRecvBandwidth": 1000,
                    "videoMinRecvBandwidth": 300,
                    "videoMaxSendBandwidth": 1000,
                    "videoMinSendBandwidth": 300
                },
                "customIceServers": [
                    {
                        "url": "turn:turn-domain.com:443",
                        "username": "usertest",
                        "credential": "userpass"
                    }
                ]
            }
        }
        sessionFunc.joinSession(url, data);
    
    }
    return(
        <button onClick={onClick}> 
            <img src='../../assets/mediaIcon/startCallIcon' />
        </button>
    )
}

const EndBtn = () => {
    function onClick(){
        sessionFunc.leaveSession();
    }
    return(
        <button onClick={onClick}>
            <img src='../../assets/mediaIcon/endCallIcon' />
        </button>
    )
}

export default { StartBtn, EndBtn };