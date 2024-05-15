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

// const URL = process.env.NODE_ENV === 'production' ? '' : 'https://demos.openvidu.io/';
const URL = 'http://localhost:5000/'

export default function MediaCall() {
    const [mySessionId, setMySessionId] = useState();
    const [myUserName, setMyUserName] = useState(`Participant${Math.floor(Math.random() * 100)}`);
    const [session, setSession] = useState(undefined);
    const [mainStreamManager, setMainStreamManager] = useState(undefined);
    const [publisher, setPublisher] = useState(undefined);
    const [subscribers, setSubscribers] = useState([]);
    const [currentVideoDevice, setCurrentVideoDevice] = useState(null);
    
    
  const [ isConnected, setIsConnected ] = useState(false);
  const [ screenCam, setScreenCam ] = useState(false);
  const [ isCameraConnected, setIsCameraConnected ] = useState(false);
  const [ isMiceConnected, setIsMiceConnected ] = useState(false);
    const OV = useRef(new OpenVidu());

    
    const { serverId, channelId } = useParams();
    const newSessionId = `${serverId}${channelId}`;
    // setMySessionId(newSessionId);

    const handleChangeSessionId = useCallback((e) => {
        setMySessionId(e.target.value);
    }, []);

    const handleChangeUserName = useCallback((e) => {
        setMyUserName(e.target.value);
    }, []);

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
            deleteSubscriber(event.stream.streamManager);
        });

        mySession.on('exception', (exception) => {
            console.warn(exception);
        });

        setSession(mySession);
    }, []);

    useEffect(() => {
        if (session) {
            // Get a token from the OpenVidu deployment
            getToken().then(async (token) => {
                try {
                    await session.connect(token, { clientData: myUserName });

                    let publisher = await OV.current.initPublisherAsync(undefined, {
                        audioSource: undefined,
                        videoSource: undefined,
                        publishAudio: true,
                        publishVideo: true,
                        resolution: '640x480',
                        frameRate: 30,
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
                } catch (error) {
                    console.log('There was an error connecting to the session:', error.code, error.message);
                }
            });
        }
    }, [session, myUserName]);

    const leaveSession = useCallback(() => {
        // Leave the session
        if (session) {
            session.disconnect();
        }
    
        // Reset all states and OpenVidu object
        OV.current = new OpenVidu();
        setSession(undefined);
        setSubscribers([]);
        setMySessionId(newSessionId);
        setMyUserName('Participant' + Math.floor(Math.random() * 100));
        setMainStreamManager(undefined);
        setPublisher(undefined);
    }, [session]);

    const switchCamera = useCallback(async () => {
        try {
            const devices = await OV.current.getDevices();
            const videoDevices = devices.filter(device => device.kind === 'videoinput');
    
            if (videoDevices && videoDevices.length > 1) {
                const newVideoDevice = videoDevices.filter(device => device.deviceId !== currentVideoDevice.deviceId);
    
                if (newVideoDevice.length > 0) {
                    const newPublisher = OV.current.initPublisher(undefined, {
                        videoSource: newVideoDevice[0].deviceId,
                        publishAudio: true,
                        publishVideo: true,
                        mirror: true,
                    });
    
                    if (session) {
                        await session.unpublish(mainStreamManager);
                        await session.publish(newPublisher);
                        setCurrentVideoDevice(newVideoDevice[0]);
                        setMainStreamManager(newPublisher);
                        setPublisher(newPublisher);
                    }
                }
            }
        } catch (e) {
            console.error(e);
        }
    }, [currentVideoDevice, session, mainStreamManager]);

    const deleteSubscriber = useCallback((streamManager) => {
        setSubscribers((prevSubscribers) => {
            const index = prevSubscribers.indexOf(streamManager);
            if (index > -1) {
                const newSubscribers = [...prevSubscribers];
                newSubscribers.splice(index, 1);
                return newSubscribers;
            } else {
                return prevSubscribers;
            }
        });
    }, []);

    useEffect(() => {
        const handleBeforeUnload = (event) => {
            leaveSession();
        };
        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [leaveSession]);

    /**
     * --------------------------------------------
     * GETTING A TOKEN FROM YOUR APPLICATION SERVER
     * --------------------------------------------
     * The methods below request the creation of a Session and a Token to
     * your application server. This keeps your OpenVidu deployment secure.
     *
     * In this sample code, there is no user control at all. Anybody could
     * access your application server endpoints! In a real production
     * environment, your application server must identify the user to allow
     * access to the endpoints.
     *
     * Visit https://docs.openvidu.io/en/stable/application-server to learn
     * more about the integration of OpenVidu in your application server.
     */
    const getToken = useCallback(async () => {
        return createSession(newSessionId).then(sessionId =>
            createToken(sessionId),
        );
    }, [mySessionId]);

    const createSession = async (sessionId) => {
        const response = await axios.post(URL + 'api/sessions', { customSessionId: sessionId }, {
            headers: { 'Content-Type': 'application/json', },
        });
        return response.data; // The sessionId
    };

    const createToken = async (sessionId) => {
        const response = await axios.post(URL + 'api/sessions/' + sessionId + '/connections', {}, {
            headers: { 'Content-Type': 'application/json', },
        });
        return response.data; // The token
    };
      const toggleCamera = () => {
        // videoDevice.enabled = !videoDevice.enabled;
        setIsCameraConnected(videoDevice.enabled);
      };

      const toggleMice = () => {
        // audioDevice.enabled = !audioDevice.enabled;
        setIsMiceConnected(audioDevice.enabled);
      };

    return (
      <>
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

        {session === undefined ? (
          <div>
            <h2> {channelId} </h2>
              <div className={style.mediaDeviceContainer}>
                <button style={{backgroundColor: 'Green'}} onClick={joinSession}> <IoCall /> </button>
              </div>
          </div>
      ) : null}

      {session !== undefined ? (
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
              <button style={{backgroundColor: 'Red'}} onClick={leaveSession}> <IoCallOutline /> </button>
            {/* </div> */}

              {mainStreamManager !== undefined ? (
                  <div id="main-video" className="col-md-6">
                      <UserVideoComponent streamManager={mainStreamManager} />
                  </div>
              ) : null}
                  {subscribers.map((sub, i) => (
                      <div key={sub.id} className="stream-container col-md-6 col-xs-6" onClick={() => handleMainVideoStream(sub)}>
                          <span>{sub.id}</span>
                          <UserVideoComponent streamManager={sub} />
                      </div>
                  ))}
              </div>
      ) : null}
  </div>
      </>
    );
}

const header = () =>{
  return(
    <>
    <div className={style.header}>
      {/* <img src='./../' */}
    </div>
    </>
  )
}


{/* <div id="video-container" className="col-md-6">
    {publisher !== undefined ? (
        <div className="stream-container col-md-6 col-xs-6" onClick={() => handleMainVideoStream(publisher)}>
            <UserVideoComponent streamManager={publisher} />
        </div>
    ) : null} */}