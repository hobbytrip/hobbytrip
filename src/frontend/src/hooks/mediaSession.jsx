import { Openvidu } from 'openvidu-browser';
import { useMutation } from 'react-query';
import  axios  from 'axios';

let OV, session;

const { postSession } = useMutation((url, data) => {
    axios.post(url, data);
});

// 방 이름? 채널 이름이랑 누른 유저 id를 가져오고 싶은데 어떻게 해야하지??
const createSession = (url, data) =>{
    OV = new Openvidu();
    session = OV.initSession();

    postSession(url, data);

    session.on('sessionCreated', function(){  
        console.log('session Created');        
    });
}

const joinSession = (url, data) => {
    postSession(url, data);
}

const leaveSession = () => {
    session.disconnect();
}

window.onbeforeunload = function(){
    if(session) session.disconnect();
};

export default { createSession, leaveSession, joinSession };