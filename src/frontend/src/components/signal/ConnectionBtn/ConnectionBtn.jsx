import sessionFunc from '../../../hooks/mediaSession';
import { useState, useEffect } from 'react';
import axios from 'axios';
import useMediaConnectedStore from '../../../actions/mediaState';
import styles from './ConnectionBtn.module.css';

const SERVER_URL = 'http://localhost:3000/';

const StartBtn = () => {
    const [channel, setChannel] = useState({});
    const [session, setSession] = useState({});
    const { setMediaConnected } = useMediaConnectedStore(state => state);

    useEffect(() => {
        getChannel;
    }, []);

    const getChannel = () => {
        axios.get(`${SERVER_URL}`)
        .then(res => {
            setChannel(res)
            getSession;
        })
        .catch(err => console.log(err));
    }

    const getSession = () => {
        axios.get(`${SERVER_URL}/openvidu/api/sessions/${serverId}/${channelId}`)
        .then(res => {
            setSession(res)
            console.log(res)
        })
        .catch(err => console.log(err));
    }

    function onClick(){
        // 서버, 채널 정보 요청하기
        if(session.sessionId === ''){
            // 세션이 존재하지 않으므로 생성하기            
            const url = SERVER_URL + 'vid/openvidu/api/sessions';
            const data = {
                header: {
                    'Authorization': 'Basic EncodeBase64', // + EncodeBase64(OPENVIDUAPP:<YOUR_SECRET>), 토큰?
                    'Content-Type': 'application/json',
                    'Token': '' // 토큰 내용
                },
                body: {
                    "mediaMode": "ROUTED",
                    "object": "session",
                    "customSessionId": `${serverId}/${channelId}`,
                    "forcedVideoCodec": "VP8",
                    "allowTranscoding": false
                }
            }
            sessionFunc.createSession(url, data);   // session 생성
            setSession(getSession);
        }

        // 세션이 없으면 생성 후 접속, 있으면 바로 접속
        const url = SERVER_URL + `vid/openvidu/api/sessions/${session.sessionId}/connection`;
        const data = {
            header: {
                'Authorization': 'Basic EncodeBase64', // + EncodeBase64(OPENVIDUAPP:<YOUR_SECRET>), 토큰?
                'Content-Type': 'application/json',
                'Token': '' // 토큰 내용
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
                // "customIceServers": [
                //     {
                //         "url": "turn:turn-domain.com:443",
                //         "username": "usertest",
                //         "credential": "userpass"
                //     }
                // ]
            }
        }
        sessionFunc.joinSession(url, data);
        setMediaConnected(true);
    }
    return(
        <button onClick={onClick} className='styles.button'> 
            <img src='../../../src/assets/media/startCall.png'/>
        </button>
    )
}

const EndBtn = () => {
    function onClick(){
        setMediaConnected(false);
    }
    return(
        <button onClick={onClick} className='.button'>
            <img src='../../../src/assets/media/endCall.png' />
        </button>
    )
}

export default { StartBtn, EndBtn };