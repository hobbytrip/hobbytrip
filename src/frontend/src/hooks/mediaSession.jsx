import { Openvidu } from 'openvidu-browser';
import { useMutation } from 'react-query';
import  axios  from 'axios';

let OV, session;

const postSession = (url, data) => {
    axios.post(url, data)
    .then(res => console.log(res))
    .catch(err => console.log(err));
};

const createSession = (url, data) =>{
    OV = new Openvidu();
    session = OV.initSession();

    useMutation(postSession(url, data));

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