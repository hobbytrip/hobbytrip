import { useEffect, useState, useRef, useCallback } from 'react';
import { OpenVidu } from 'openvidu-browser';
import { useLocation, useParams } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import axios from 'axios';
import style from './MediaCall.module.css';
import UserVideoComponent from '../UserVideoComponent';

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

const URL = '';

const MediaCall = () => {
  const [session, setSession] = useState(undefined);  // 세션 정보
  const [mainStreamManager, setMainStreamManager] = useState(undefined);
  const [publisher, setPublisher] = useState(null);
  const [subscribers, setSubscribers] = useState([]);
  const [videoDevice, setVideoDevice] = useState(null);
  const [audioDevice, setAudioDevice] = useState(null);
  const [ isConnected, setIsConnected ] = useState(false);
  const [ screenCam, setScreenCam ] = useState(false);
  const [ isCameraConnected, setIsCameraConnected ] = useState(false);
  const [ isMiceConnected, setIsMiceConnected ] = useState(false);
  const [cookie, setCookie] = useCookies(['jwtToken'])
  const [jwtToken, setJwtToken] = useState(null);

  const OV = useRef(new OpenVidu());
  
  const { serverId, channelId } = useParams();
  const newSessionId = `${serverId}/${channelId}`;

  useEffect(() => {
    // 쿠키에서 JWT 토큰 가져오기
    const token = cookie.jwtToken;
    setJwtToken(token);
  }, [cookie]);

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

  const mediaDeivceSetting = async () => {
    const devices = await OV.current.getDevices();
    const videoDevices = devices.filter(device => device.kind === 'videoinput');
    const videoDeviceId = publisher.stream.getMediaStream().getVideoTracks()[0].getSettings().deviceId;
    setVideoDevice(videoDevices.find(device => device.deviceId === videoDeviceId));

    const audioDevices = devices.filter(device => device.kind === 'audioinput');
    const audioDeviceId = publisher.stream.getMediaStream().getAudioTracks()[0].getSettings().deviceId;
    setAudioDevice(audioDevices.find(device => device.deviceId === audioDeviceId));
  }

  useEffect(() => {
    if (session) {  // 세션이 이미 존재할 때
      getToken().then(async () => {  
        try {
          await session.connect(jwtToken, { clientData: myUserName });
          mediaDeivceSetting();

          const publisher = await OV.current.initPublisherAsync(undefined, {
              audioSource: audioDevice,
              videoSource: videoDevice,
              publishAudio: true,
              publishVideo: true,
              // resolution: '640x480',
              // frameRate: 30,
              // insertMode: 'APPEND',
              mirror: false,
          });

          session.publish(publisher);

          setMainStreamManager(publisher);
          setPublisher(publisher);
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
    const res = await axios.post(URL + 'api/sessions', { customSessionId: sessionId }, {
      headers: { 'Content-Type': 'application/json', },
    });
    return res.data; 
  };

  const createToken = async (sessionId) => {
    const res = await axios.post(URL + 'api/sessions/' + sessionId + '/connections', {}, {
      headers: { 'Content-Type': 'application/json', },
    });
    return res.data; 
  };

  const leaveSession = useCallback(() => {
    if (session) {
      session.disconnect();
    }

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
  //       const newVideoDevice = videoDevices.filter(device => device.deviceId !== videoDevice.deviceId);

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
  //           setVideoDevice(newVideoDevice[0]);
  //           setMainStreamManager(newPublisher);
  //           setPublisher(newPublisher);
  //         }
  //       }
  //     }
  //   } catch (e) {
  //     console.error(e);
  //   }
  // }, [videoDevice, session, mainStreamManager]);

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
    videoDevice.enabled = !videoDevice.enabled;
    setIsCameraConnected(videoDevice.enabled);
  };

  const toggleMice = () => {
    audioDevice.enabled = !audioDevice.enabled;
    setIsMiceConnected(audioDevice.enabled);
  };

  return (
    <>
    <div className={style.wrapper}>
      <div className={style.videoContainer}>
        <RoomHeader />
        <div>
          {publisher !== undefined ? (
              <div onClick={() => handleMainVideoStream(publisher)}>
                  <UserVideoComponent
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
        <UserVideoComponent autoPlay />
      </div>

      <div className={style.mediaDeviceContainer}>
        <button onClick={() => setScreenCam(!screenCam)}>
          { screenCam ? (<LuMonitorOff />) : (<LuMonitor />) }
        </button>
        <button onClick={toggleCamera}>
          { isCameraConnected ? (<IoVideocamOffOutline />) : (<IoVideocamOutline />)} 
        </button>
        <button onClick={toggleMice}> 
          { isMiceConnected ? (<MdOutlineKeyboardVoice />) : (<MdKeyboardVoice />)} 
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

const RoomHeader = () => {
  return(
    <>
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
    </>
  )
}

export default MediaCall;
