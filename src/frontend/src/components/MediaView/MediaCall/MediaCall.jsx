import { OpenVidu } from 'openvidu-browser';
import axios from 'axios';
import { useLocation, useParams } from 'react-router-dom';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import style from './MediaCall.module.css';
import UserVideoComponent from './../UserVideoComponent';

import { HiMiniSpeakerWave } from "react-icons/hi2";
import { AiFillMessage } from "react-icons/ai";
import { IoClose, 
IoVideocamOutline, 
IoVideocamOffOutline, 
IoCall, 
IoCallOutline} 
from "react-icons/io5";
import { LuMonitor, LuMonitorOff } from "react-icons/lu";
import { MdOutlineKeyboardVoice,  MdKeyboardVoice } from "react-icons/md";

const URL = 'http://localhost:5000/'

export default function MediaCall() {
  const [mySessionId, setMySessionId] = useState();
  const [session, setSession] = useState(undefined);
  const [mainStreamManager, setMainStreamManager] = useState(undefined);
  const [publisher, setPublisher] = useState(undefined);
  const [subscribers, setSubscribers] = useState([]);
  // const [userData, setUserData] = useState([]);
  
  const [ screenCam, setScreenCam ] = useState(false);
  const [ isCameraConnected, setIsCameraConnected ] = useState(false);
  const [ isMicConnected, setIsMicConnected ] = useState(false);
  const OV = useRef(new OpenVidu());

  const { serverId, channelId } = useParams();
  const newSessionId = `${serverId}${channelId}`;

  // useEffect(() => {
  //   axios.get('http://localhost:8080/api/user/profile')
  //   .then(res => {
  //     setUserData(res);
  //   })
  //   .catch(err => {
  //     console.error(err);
  //   })
  // }, [userData]);

  const handleMainVideoStream = useCallback((stream) => {
    if (mainStreamManager !== stream) {
      setMainStreamManager(stream);
    }
  }, [mainStreamManager]);

  const joinSession = useCallback((e) => {
      e.preventDefault();
      
      const mySession = OV.current.initSession();

      mySession.on('streamCreated', (event) => {
        const subscriber = mySession.subscribe(event.stream, undefined);
        setSubscribers((subscribers) => [...subscribers, subscriber]);
      });

      mySession.on('streamDestroyed', (event) => {
        const streamManager = event.stream.streamManager;
        setSubscribers((prevSubscribers) => {
        return prevSubscribers.filter(subscriber => subscriber.stream.streamManager !== streamManager);
        });
      });

      mySession.on('exception', (exception) => {
        console.warn(exception);
      });

      setSession(mySession);
  }, []);

  useEffect(() => {
    if (session) {
      getToken().then(async (token) => {
        try {
          await session.connect(token);

          let publisher = await OV.current.initPublisherAsync(undefined, {
            audioSource: undefined,
            videoSource: undefined,
            publishAudio: true,
            publishVideo: true,
            mirror: false,
          });

          session.publish(publisher);
          setMainStreamManager(publisher);
          setPublisher(publisher);
          } catch (error) {
              console.log('There was an error connecting to the session:', error.code, error.message);
          }
        });
    }
  }, [session]);

  const leaveSession = useCallback(() => {
      if (session) {
        session.disconnect();
      }
      OV.current = new OpenVidu();
      setSession(undefined);
      setSubscribers([]);
      setMySessionId(newSessionId);
      setMainStreamManager(undefined);
      setPublisher(undefined);
  }, [session]);

  // const switchCamera = useCallback(async () => {
  //   try {
  //     const devices = await OV.current.getDevices();
  //     const videoDevices = devices.filter(device => device.kind === 'videoinput');

  //     if (videoDevices && videoDevices.length > 1) {
  //       const newVideoDevice = videoDevices.filter(device => device.deviceId !== videoDevice.deviceId);

  //       if (newVideoDevice.length > 0) {
  //         const newPublisher = OV.current.initPublisher(undefined, {
  //           videoSource: Screen,
  //           publishAudio: true,
  //           publishVideo: true,
  //         });

  //       if (session) {
  //           await session.unpublish(mainStreamManager);
  //           await session.publish(newPublisher);
  //           setVideoDevice(newVideoDevice[0]);
  //           setMainStreamManager(newPublisher);
  //           setPublisher(newPublisher);
  //       }
  //       }
  //     }
  //   } catch (e) {
  //     console.error(e);
  //   }
  // }, [videoDevice, session, mainStreamManager]);

  const switchCamera = () => {
    setScreenCam(!screenCam);
  }

  useEffect(() => {
    const handleBeforeUnload = (event) => {
      leaveSession();
    };
    window.addEventListener('beforeunload', handleBeforeUnload);

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, [leaveSession]);

  const getToken = useCallback(async () => {
    return createSession(newSessionId).then(sessionId =>
      createToken(sessionId),
    );
  }, [mySessionId]);

  const createSession = async (sessionId) => {
      const response = await axios.post(URL + 'api/sessions', { customSessionId: sessionId }, {
        headers: { 'Content-Type': 'application/json', },
        body: {
         userId: userId,
         serverId: serverId,
         channelId: channelId
        }
      });
      return response.data; 
  };
  // "/api/sessions/{sessionId}/{userId}/connections"
  const createToken = async (sessionId) => {
    const response = await axios.post(URL + 'api/sessions/' + sessionId + '/aa' + '/connections', {
      headers: { 'Content-Type': 'application/json', },
      body: {
        userId: userId,
        serverId: serverId,
        channelId: channelId
       }
    });
    return response.data;
  };
  
  const toggleVideo = () => {
    if (publisher) {
      const publishVideo = publisher.stream.videoActive;
      publisher.publishVideo(!publishVideo);
      setIsCameraConnected(!publishVideo);
    }
  };

  const toggleMic = () => {
    if (publisher) {
      const publishAudio = publisher.stream.audioActive;
      publisher.publishAudio(!publishAudio);
      setIsMicConnected(!publishAudio);
    }
  };

  return (
    <>
    <Header />
    <ChannelData />
    <div className={style.wrapper}>
      <div className={style.headerContainer}>
        <HiMiniSpeakerWave style={{ width: '15px', height: '15px'}}/>
        <h4> {channelId} </h4>
        <button>
          <AiFillMessage style={{ width: '18.66px', height: '18.66px'}} />
        </button>
        <button>
          <IoClose style={{ width: '18px', height: '18px'}} />
        </button>
      </div>

      <div className={style.mediaContainer}>
        {session === undefined ? (
          <div>
            <div className={style.videoContainer}>
              <h2> {channelId} </h2>
            </div>
              <div className={style.mediaDeviceContainer}>
                <button style={{backgroundColor: 'Green'}} onClick={joinSession}> <IoCall /> </button>
              </div>
          </div>
        ) : (
          <div className={style.videoContainer}>
            {/* 자기 영상 */}
            {publisher !== undefined ? (
                <div onClick={() => handleMainVideoStream(publisher)}>
                  <UserVideoComponent streamManager={publisher} />
                </div>
              ) : (null)}
            {/* 딴 사람들 영상 */}
            {subscribers.map((sub, i) => (
              <div key={sub.id} onClick={() => handleMainVideoStream(sub)}>
                  <span>{sub.id}</span>
                  <UserVideoComponent streamManager={sub} />
              </div>
            ))}
          <div className={style.mediaDeviceContainer}>
            <button onClick={switchCamera}>
              { screenCam ? (<LuMonitorOff />) : (<LuMonitor />) }
            </button>
            <button onClick={toggleVideo}>
              { isCameraConnected ? (<IoVideocamOffOutline />) : (<IoVideocamOutline />)} 
            </button>
            <button onClick={toggleMic}> 
              { isMicConnected ? (<MdOutlineKeyboardVoice />) : (<MdKeyboardVoice />)} 
            </button>
            <button style={{backgroundColor: 'Red'}} onClick={leaveSession}>
              <IoCallOutline />
            </button>
          </div>
        </div>
        )}
      </div>
    </div>
    </>
  );
}

const Header = () =>{
  return(
    <>
    <div className={style.header}>
        {/* <img src='./../../../../src/assets/image/logo.png'/> */}
    </div>
    </>
  )
}

const ChannelData = () => {
  return(
    <>
    <div className={style.channelData}>
      <p> iiii </p>
    </div>
    </>
  )
}

// const UserData = () => {
//   return(
//     <div>
//       <h5> 유저 아이디 </h5>
//       <img> 유저 사진</img>
//     </div>
//   )
// }