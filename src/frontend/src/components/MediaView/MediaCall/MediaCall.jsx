import { OpenVidu } from 'openvidu-browser';
import { useParams, useNavigate, json } from 'react-router-dom';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import style from './MediaCall.module.css';
import axios from 'axios';

import ChatHeader from '../../Common/ChatRoom/CommunityChatHeader/ChatHeader';
import ChatHeaderModal from '../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo';
import UserVideoComponent from './../UserVideoComponent';
import useUserStore from '../../../actions/useUserStore';
import API from '../../../utils/API/API';

import { HiMiniSpeakerWave } from "react-icons/hi2";
import { AiFillMessage } from "react-icons/ai";
import { IoClose, IoVideocamOutline, IoVideocamOffOutline, IoCall, IoCallOutline } from "react-icons/io5";
import { LuMonitor, LuMonitorOff } from "react-icons/lu";
import { MdOutlineKeyboardVoice, MdKeyboardVoice } from "react-icons/md";
import { axiosInstance } from '../../../utils/axiosInstance';

const URL = API.MEDIA;

export default function MediaCall() {
  const [session, setSession] = useState(undefined);
  const [publisher, setPublisher] = useState(undefined);
  const [subscribers, setSubscribers] = useState([]);
  const [screenCam, setScreenCam] = useState(false);
  const [isCameraConnected, setIsCameraConnected] = useState(false);
  const [isMicConnected, setIsMicConnected] = useState(false);
  
  let { serverId, channelId } = useParams();
  const newSessionId = `${serverId}${channelId}`;
  console.log(newSessionId);
  const { userId } = useUserStore();
  const nav = useNavigate();
  serverId = parseInt(serverId);
  channelId = parseInt(channelId);
  // console.log(typeof newSessionId);
  // console.log(typeof serverId);
  // console.log(typeof channelId);
  // console.log(typeof userId);

  const OV = useRef(new OpenVidu());

  const accessToken = localStorage.getItem('accessToken');
  // 세션 참여 후 웹소켓과의 통신
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

  // publisher 설정
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
          setPublisher(publisher);
        } catch (error) {
          console.log(error);
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
    // console.log(sessionId);
    const data = JSON.stringify({
      userId: userId,
      channelId: channelId,
      serverId: serverId
    })
    const res = await axios.post(URL, { customSessionId: sessionId }, {
      headers: { 
        // Content-Type: 'application/json',
        Authorization: `Bearer ${accessToken}`,
      },
      body: {
        data
      },
      withCredentials: true
  })
    console.log(res);
    return res.data;
  };

  const createToken = async (sessionId) => {
    console.error(accessToken)
    // console.log(sessionId)
    // console.log(newSessionId)
    // console.log(token)
    // const data = 
    // console.log(data)'
    const req = {
      userId: userId,
      channelId: channelId,
      serverId: serverId
    }
    console.error(JSON.stringify(req));
    const res = await axios.post(`${URL}/${sessionId}/connections`, {
      headers: { 
        // Content-Type: 'application/json',
        Authorization: `Bearer ${accessToken}`,
      },
      body: {
        userId: userId,
        channelId: channelId,
        serverId: serverId
      },
      withCredentials: true
    });
    
    console.log(res.data)
    return res.data;
  };

  // 세션 나가기
  const leaveSession = useCallback(() => {
    if (session) {
      session.disconnect();
      axiosInstance.delete(`${URL}/${newSessionId}/disconnect`, {
        data: {
          body: {
            userId: userId,
            channelId: channelId,
            serverId: serverId
          }
        }});
    }
    OV.current = new OpenVidu();

    setSession(undefined);
    setSubscribers([]);
    setPublisher(undefined);
  }, [session, userId]);

  useEffect(() => {
    const handleBeforeUnload = () => {
      leaveSession();
    };
    window.addEventListener('beforeunload', handleBeforeUnload);

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, [leaveSession]);

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

  const switchCamera = () => {
    setScreenCam(!screenCam);
  };

  const handleClose = () => {
    nav(-1);
  }

  return (
    <>
    <div className={style.wrapper}>
    <ChatHeader />
    <ChatHeaderModal />
      <div className={style.container}>
        <div className={style.headerContainer}>
          <HiMiniSpeakerWave style={{ width: '15px', height: '15px' }} />
          <h4> {channelId} </h4>
          <button>
            <AiFillMessage style={{ width: '18.66px', height: '18.66px' }} />
          </button>
          <button>
            <IoClose style={{ width: '18px', height: '18px' }} onClick={handleClose}/>
          </button>
        </div>

        <div className={style.mediaContainer}>
          {session === undefined ? (
            <>
            <div className={style.videoContainer}>
                <h3> 통화에 참여하시겠습니까? </h3>
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
              {subscribers.slice(0, 5).map((sub, i) => (
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
      </div>
    </>
  );
}
