import { OpenVidu } from 'openvidu-browser';
import axios from 'axios';
import { useLocation, useParams } from 'react-router-dom';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import style from './MediaCall.module.css';
import UserVideoComponent from './../UserVideoComponent';
import useUserStore from '../../../actions/useUserStore';

import { HiMiniSpeakerWave } from "react-icons/hi2";
import { AiFillMessage } from "react-icons/ai";
import { IoClose, IoVideocamOutline, IoVideocamOffOutline, IoCall, IoCallOutline } from "react-icons/io5";
import { LuMonitor, LuMonitorOff } from "react-icons/lu";
import { MdOutlineKeyboardVoice, MdKeyboardVoice } from "react-icons/md";

const URL = 'http://localhost:5000/';

export default function MediaCall() {
  const [session, setSession] = useState(undefined);
  const [mainStreamManager, setMainStreamManager] = useState(undefined);
  const [publisher, setPublisher] = useState(undefined);
  const [subscribers, setSubscribers] = useState([]);
  const [userData, setUserData] = useState({});
  const [screenCam, setScreenCam] = useState(false);
  const [isCameraConnected, setIsCameraConnected] = useState(false);
  const [isMicConnected, setIsMicConnected] = useState(false);

  const { getUserInfo } = useUserStore((state) => state);
  const OV = useRef(new OpenVidu());

  const { serverId, channelId } = useParams();
  const newSessionId = `${serverId}${channelId}`;

  const userId = 'userId' + Math.floor(Math.random() * 10); // 테스트 용 -> res.data.userId로 바꿔주세용
      
  useEffect(() => {
    getUserInfo()
      .then(res => {
        const data = {
          userId: userId,
          serverId: serverId,
          channelId: channelId
        };
        setUserData(data);
      })
      .catch(err => console.error(err));
  }, [getUserInfo, serverId, channelId]);

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
        return prevSubscribers.filter(subscriber => subscriber !== streamManager);
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
          console.log(error);
        }
      });
    }
  }, [session]);

  const leaveSession = useCallback(() => {
    if (session) {
      session.disconnect();
      axios.delete(`${URL}/api/sessions/${newSessionId}/disconnect`, {
        data: {
          body: userData
        }});
    }
    OV.current = new OpenVidu();

    setSession(undefined);
    setSubscribers([]);
    setMainStreamManager(undefined);
    setPublisher(undefined);
  }, [session, userData]);

  const switchCamera = () => {
    setScreenCam(!screenCam);
  };

  useEffect(() => {
    const handleBeforeUnload = () => {
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
  }, [newSessionId]);

  const createSession = async (sessionId) => {
    const res = await axios.post(`${URL}api/sessions`, 
    { customSessionId: sessionId }, {
      headers: { 'Content-Type': 'application/json' },
      body: userData
    });
    return res.data;
  };

  const createToken = async (sessionId) => {
    const res = await axios.post(`${URL}api/sessions/${sessionId}/${userId}/connections`, {
      headers: { 'Content-Type': 'application/json' },
      body: userData
    });
    return res.data;
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
          <HiMiniSpeakerWave style={{ width: '15px', height: '15px' }} />
          <h4> {channelId} </h4>
          <button>
            <AiFillMessage style={{ width: '18.66px', height: '18.66px' }} />
          </button>
          <button>
            <IoClose style={{ width: '18px', height: '18px' }} />
          </button>
        </div>

        <div className={style.mediaContainer}>
          {session === undefined ? (
            <>
            <div className={style.videoContainer}>
              <h2> {channelId} </h2>
            </div>
            <div className={style.mediaDeviceContainer}>
              <button style={{ backgroundColor: 'Green' }} onClick={joinSession}> <IoCall /> </button>
            </div>
            </>
          ) : (
            <div className={style.videoContainer}>
              {publisher && (
                <div>
                <UserVideoComponent streamManager={publisher} />
                </div>
              )}
              {subscribers.map((sub, i) => (
                <div key={i} >
                  <UserVideoComponent streamManager={sub} />
                </div>
              ))}
              <div className={style.mediaDeviceContainer}>
                <button onClick={switchCamera}>
                  {screenCam ? (<LuMonitorOff />) : (<LuMonitor />)}
                </button>
                <button onClick={toggleVideo}>
                  {isCameraConnected ? (<IoVideocamOffOutline />) : (<IoVideocamOutline />)}
                </button>
                <button onClick={toggleMic}>
                  {isMicConnected ? (<MdOutlineKeyboardVoice />) : (<MdKeyboardVoice />)}
                </button>
                <button style={{ backgroundColor: 'Red' }} onClick={leaveSession}>
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

const Header = () => (
  <div style={{
    height: '36px',
    padding: '10px 10px 10px 0px',
    gap: '260px'
  }}/>
);

const ChannelData = () => ( 
  <div style={{
    height: '34px',
    padding: '16px 29px',
    gap: '10px'
  }}/>
);
