import { useEffect, useState, useRef, useCallback } from 'react';
import { OpenVidu } from 'openvidu-browser';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import style from './MediaCall.module.css';
import useMediaSessionStore from '../../../actions/useMediaSessionStore'; 
import UserVideo from './../UserVideo';

import { HiMiniSpeakerWave } from "react-icons/hi2";
import { AiFillMessage } from "react-icons/ai";
import { IoClose, 
  IoVideocamOutline, 
  IoVideocamOffOutline, 
  IoCall, 
  IoCallOutline } 
from "react-icons/io5";
import { LuMonitor, LuMonitorOff } from "react-icons/lu";
import { MdOutlineKeyboardVoice,  MdKeyboardVoice } from "react-icons/md";

const newSessionId = 'asdfa';
const URL = '';

const MediaCall = () => {
  const [ isConnected, setIsConnected ] = useState(false);
  const [ screenCam, setScreenCam ] = useState(false);
  const [ camera, setCamera ] = useState(false);
  const [ mice, setMice ] = useState(false);

  // const [newSessionId, setMySessionId] = useState()
  const [session, setSession] = useState(undefined);  // 세션 정보
  const [mainStreamManager, setMainStreamManager] = useState(undefined);
  const [publisher, setPublisher] = useState(null);
  const [subscribers, setSubscribers] = useState([]);
  const [currentVideoDevice, setCurrentVideoDevice] = useState(null);

  const OV = useRef(new OpenVidu());

  const handleMainVideoStream = useCallback((stream) => {
    if (mainStreamManager !== stream) {
      setMainStreamManager(stream);
    }
  }, [mainStreamManager]);

  const joinSession = useCallback(() => {
    const mySession = OV.current.initSession();

    mySession.on('streamCreated', (event) => {
      const subscriber = mySession.subscribe(event.stream, undefined);
      setSubscribers((subscribers) => [...subscribers, subscriber]);
    });

    mySession.on('streamDestroyed', (event) => {
      deleteSubscriber(event.stream.streamManager);
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
          await session.connect(token, { clientData: myUserName });
          await OV.getUserMedia({
            audioSource: true,
            videoSource: undefined,
            resolution: '1280x720',
            frameRate: 10,
          })
          let publisher = await OV.current.initPublisherAsync(undefined, {
              audioSource: undefined,
              videoSource: undefined,
              publishAudio: true,
              publishVideo: true,
              // resolution: '640x480',
              // frameRate: 30,
              insertMode: 'APPEND',
              mirror: false,
          });

          session.publish(publisher);

          const devices = await OV.current.getDevices();
          const videoDevices = devices.filter(device => device.kind === 'videoinput');
          const currentVideoDeviceId = publisher.stream.getMediaStream().getVideoTracks()[0].getSettings().deviceId;
          const currentVideoDevice = videoDevices.find(device => device.deviceId === currentVideoDeviceId);

          setMainStreamManager(publisher);
          setPublisher(publisher);
          setCurrentVideoDevice(currentVideoDevice);
          setIsConnected(true);
      } catch (error) {
          console.log('There was an error connecting to the session:', error.code, error.message);
      }
    });
  }
}, [session]);

  const getToken = useCallback(async () => {
    return createSession(newSessionId).then(sessionId =>
      createToken(sessionId),
    );
  }, [newSessionId]);

  const createSession = async (sessionId) => {
    const response = await axios.post(URL + 'openvidu/api/sessions', { customSessionId: sessionId }, {
      headers: { 'Content-Type': 'application/json', },
    });
    return response.data; 
  };

  const createToken = async (sessionId) => {
    const response = await axios.post(URL + 'openvidu/api/sessions/' + sessionId + '/connections', {}, {
      headers: { 'Content-Type': 'application/json', },
    });
    return response.data; 
  };

  const leaveSession = useCallback(() => {
    if (session) {
      session.disconnect();
    }

    OV.current = new OpenVidu();
    setSession(undefined);
    setSubscribers([]);
    setMainStreamManager(undefined);
    setPublisher(null);
    setIsConnected(false);
  }, [session]);

  // const switchCamera = useCallback(async () => {
  //   try {
  //     const devices = await OV.current.getDevices();
  //     const videoDevices = devices.filter(device => device.kind === 'videoinput');

  //     if (videoDevices && videoDevices.length > 1) {
  //       const newVideoDevice = videoDevices.filter(device => device.deviceId !== currentVideoDevice.deviceId);

  //       if (newVideoDevice.length > 0) {
  //         const newPublisher = OV.current.initPublisher(undefined, {
  //           videoSource: newVideoDevice[0].deviceId,
  //           publishAudio: true,
  //           publishVideo: true,
  //           mirror: true,
  //         });

  //         if (session) {
  //           await session.unpublish(mainStreamManager);
  //           await session.publish(newPublisher);
  //           setCurrentVideoDevice(newVideoDevice[0]);
  //           setMainStreamManager(newPublisher);
  //           setPublisher(newPublisher);
  //         }
  //       }
  //     }
  //   } catch (e) {
  //     console.error(e);
  //   }
  // }, [currentVideoDevice, session, mainStreamManager]);

  const deleteSubscriber = useCallback((streamManager) => {
    setSubscribers((prevSubscribers) => {
      return prevSubscribers.filter(subscriber => subscriber.stream.streamManager !== streamManager);
    });
  }, []);

  // const deleteSubscriber = useCallback((streamManager) => {
  //   setSubscribers((prevSubscribers) => {
  //     const index = prevSubscribers.indexOf(streamManager);
  //     if (index > -1) {
  //       const newSubscribers = [...prevSubscribers];
  //       newSubscribers.splice(index, 1);
  //       return newSubscribers;
  //     } else {
  //       return prevSubscribers;
  //     }
  //   });
  // }, []);

  useEffect(() => {
    const handleBeforeUnload = (event) => {
      leaveSession();
    };
    window.addEventListener('beforeunload', handleBeforeUnload);

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, [leaveSession]);

  const toggleCamera = () => {
    publisher.publishVideo(!camera);
    setCamera(!camera);
  };

  const toggleMice = () => {
    publisher.publishAudio(!mice);
    setMice(!mice);
  };

  return (
    <>
    <div className={style.wrapper}>
      <div className={style.headerContainer}>
        <HiMiniSpeakerWave style={{ width: '15px', height: '15px'}}/>
        <h4> 채널 이름 / 친구 이름 </h4>
        <button>
          <AiFillMessage style={{ width: '18.66px', height: '18.66px'}} />
        </button>
        <button>
          <IoClose style={{ width: '18px', height: '18px'}} />
        </button>
      </div>

      <div className={style.videoContainer}>
      <div>
        {publisher !== undefined ? (
            <div onClick={() => handleMainVideoStream(publisher)}>
                <UserVideo
                    streamManager={publisher} />
            </div>
        ) : null}
        {subscribers.map((sub, i) => (
            <div key={sub.id} onClick={() => handleMainVideoStream(sub)}>
                <span>{sub.id}</span>
                <UserVideoComponent streamManager={sub} />
            </div>
        ))}
    </div>
        <UserVideo autoPlay />
      </div>

      <div className={style.mediaDeviceContainer}>
        <button onClick={() => setScreenCam(!screenCam)}>
          { screenCam ? (<LuMonitorOff />) : (<LuMonitor />) }
        </button>
        <button onClick={toggleCamera}>
          { camera ? (<IoVideocamOffOutline />) : (<IoVideocamOutline />)} 
        </button>
        <button onClick={toggleMice}> 
          { mice ? (<MdOutlineKeyboardVoice />) : (<MdKeyboardVoice />)} 
        </button>
        { isConnected ? (
          <button style={{backgroundColor: 'Red'}} onClick={leaveSession}> <IoCallOutline /> </button>
        ) : (
          <button style={{backgroundColor: 'Green'}} onClick={joinSession}> <IoCall /> </button>
        )} 
      </div>
    </div>
    </>
  )
};

export default MediaCall;
