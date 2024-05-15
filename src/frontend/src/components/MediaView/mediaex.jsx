// import { useEffect, useState, useRef, useCallback } from 'react';
// import { OpenVidu } from 'openvidu-browser';
// import { useLocation, useParams } from 'react-router-dom';
// import { useCookies } from 'react-cookie';
// import axios from 'axios';
// import style from './MediaCall.module.css';
// import UserVideoComponent from '../UserVideoComponent';

// import { HiMiniSpeakerWave } from "react-icons/hi2";
// import { AiFillMessage } from "react-icons/ai";
// import { IoClose, 
//   IoVideocamOutline, 
//   IoVideocamOffOutline, 
//   IoCall, 
//   IoCallOutline} 
// from "react-icons/io5";
// import { LuMonitor, LuMonitorOff } from "react-icons/lu";
// import { MdOutlineKeyboardVoice,  MdKeyboardVoice } from "react-icons/md";

// const OV = new OpenVidu();
  
// const MediaCall = () => {
//   const [session, setSession] = useState(undefined);  // 세션 정보
//   const [mainStreamManager, setMainStreamManager] = useState(undefined);
//   const [publisher, setPublisher] = useState(null);
//   const [subscribers, setSubscribers] = useState([]);
//   const [videoDevice, setVideoDevice] = useState(null);
//   const [audioDevice, setAudioDevice] = useState(null);
//   const [ isConnected, setIsConnected ] = useState(false);
//   const [ screenCam, setScreenCam ] = useState(false);
//   const [ isCameraConnected, setIsCameraConnected ] = useState(false);
//   const [ isMiceConnected, setIsMiceConnected ] = useState(false);
  
//     const [currentVideoDevice, setCurrentVideoDevice] = useState(null);
//   // const [cookie, setCookie] = useCookies(['token'])
//   // const [token, setToken] = useState(null);

//   const { serverId, channelId } = useParams();
//   const mySessionId = `${serverId}${channelId}`;

//   // useEffect(() => {
//   //   // 쿠키에서 JWT 토큰 가져오기
//   //   const token = cookie.token;
//   //   setToken(token);
//   // }, [cookie]);

//   const handleMainVideoStream = useCallback((stream) => {
//     if (mainStreamManager !== stream) {
//       setMainStreamManager(stream);
//     }
//   }, [mainStreamManager]);

//   const joinSession = useCallback(() => {
//     const mySession = OV.initSession();

//     mySession.on('streamCreated', (event) => {
//       const subscriber = mySession.subscribe(event.stream, undefined);
//       setSubscribers((subscribers) => [...subscribers, subscriber]);
//     });

//     mySession.on('streamDestroyed', (event) => {
//       deleteSubscriber(event.stream.streamManager);
//     });

//     mySession.on('exception', (exception) => {
//       console.warn(exception);
//     });

//     setSession(mySession);

//     // return mySession.disconnect();
//   }, []);
  
//   // const deleteSubscriber = useCallback((streamManager) => {
//   //   setSubscribers((prevSubscribers) => {
//   //     return prevSubscribers.filter(subscriber => subscriber.stream.streamManager !== streamManager);
//   //   });
//   // }, []);

//   const deleteSubscriber = useCallback((streamManager) => {
//     setSubscribers((prevSubscribers) => {
//       const index = prevSubscribers.indexOf(streamManager);
//       if (index > -1) {
//         const newSubscribers = [...prevSubscribers];
//         newSubscribers.splice(index, 1);
//         return newSubscribers;
//       } else {
//         return prevSubscribers;
//       }
//     });
//   }, []);

//   const mediaDeivceSetting = async () => {
//     const devices = await OV.getDevices();
//     const videoDevices = devices.filter(device => device.kind === 'videoinput');
//     const videoDeviceId = publisher.stream.getMediaStream().getVideoTracks()[0].getSettings().deviceId;
//     setVideoDevice(videoDevices.find(device => device.deviceId === videoDeviceId));

//     const audioDevices = devices.filter(device => device.kind === 'audioinput');
//     const audioDeviceId = publisher.stream.getMediaStream().getAudioTracks()[0].getSettings().deviceId;
//     setAudioDevice(audioDevices.find(device => device.deviceId === audioDeviceId));
//   }

//       useEffect(() => {
//         if (session) {
//             // Get a token from the OpenVidu deployment
//             getToken().then(async (token) => {
//                 try {
//                     await session.connect(token);

//                     let publisher = await OV.current.initPublisherAsync(undefined, {
//                         audioSource: undefined,
//                         videoSource: undefined,
//                         publishAudio: true,
//                         publishVideo: true,
//                         resolution: '640x480',
//                         frameRate: 30,
//                         insertMode: 'APPEND',
//                         mirror: false,
//                     });

//                     session.publish(publisher);

//                     const devices = await OV.current.getDevices();
//                     const videoDevices = devices.filter(device => device.kind === 'videoinput');
//                     const currentVideoDeviceId = publisher.stream.getMediaStream().getVideoTracks()[0].getSettings().deviceId;
//                     const currentVideoDevice = videoDevices.find(device => device.deviceId === currentVideoDeviceId);

//                     setMainStreamManager(publisher);
//                     setPublisher(publisher);
//                     setCurrentVideoDevice(currentVideoDevice);
//                 } catch (error) {
//                     console.log('There was an error connecting to the session:', error.code, error.message);
//                 }
//             });
//         }
//     }, [session]);

// const getToken = useCallback(async () => {
//   return createSession(mySessionId)
//   .then(sessionId =>
//       createToken(sessionId),
//   );
// }, [mySessionId]);

// const URL = 'http://localhost:5000';

// const createSession = async (sessionId) => {
//   const response = await axios.post(URL + '/api/sessions', { customSessionId: sessionId }, {
//       headers: { 'Content-Type': 'application/json', },
//       withCredentials: true,
//   },);
//   return response.data; // The sessionId
// };

// const createToken = async (sessionId) => {
//   const response = await axios.post(URL + '/api/sessions/' + sessionId + '/connections', {}, {
//       headers: { 'Content-Type': 'application/json', },
//       // withCredentials: true,
//   });
//   return response.data; // The token
// };

//   const leaveSession = useCallback(() => {

//     session.disconnect();

//     setSession(undefined);
//     setSubscribers([]);
//     setMainStreamManager(undefined);
//     setPublisher(null);
//     setIsConnected(false);
//   }, []);

//   // const switchCamera = useCallback(async () => {
//   //   try {
//   //     const devices = await OV.current.getDevices();
//   //     const videoDevices = devices.filter(device => device.kind === 'videoinput');

//   //     if (videoDevices && videoDevices.length > 1) {
//   //       const newVideoDevice = videoDevices.filter(device => device.deviceId !== videoDevice.deviceId);

//   //       if (newVideoDevice.length > 0) {
//   //         const newPublisher = OV.current.initPublisher(undefined, {
//   //           videoSource: newVideoDevice[0].deviceId,
//   //           publishAudio: true,
//   //           publishVideo: true,
//   //           mirror: true,
//   //         });

//   //         if (session) {
//   //           await session.unpublish(mainStreamManager);
//   //           await session.publish(newPublisher);
//   //           setVideoDevice(newVideoDevice[0]);
//   //           setMainStreamManager(newPublisher);
//   //           setPublisher(newPublisher);
//   //         }
//   //       }
//   //     }
//   //   } catch (e) {
//   //     console.error(e);
//   //   }
//   // }, [videoDevice, session, mainStreamManager]);

//   useEffect(() => {
//     const handleBeforeUnload = (event) => {
//       leaveSession();
//     };
//     window.addEventListener('beforeunload', handleBeforeUnload);

//     return () => {
//       window.removeEventListener('beforeunload', handleBeforeUnload);
//     };
//   }, [leaveSession]);

//   const toggleCamera = () => {
//     videoDevice.enabled = !videoDevice.enabled;
//     setIsCameraConnected(videoDevice.enabled);
//   };

//   const toggleMice = () => {
//     audioDevice.enabled = !audioDevice.enabled;
//     setIsMiceConnected(audioDevice.enabled);
//   };

//   return (
//     <>
//     <div className={style.wrapper}>
        
//       <div className={style.headerContainer}>
//         <HiMiniSpeakerWave style={{ width: '15px', height: '15px'}}/>
//         <h4> {channelId} </h4>
//         <button>
//           <AiFillMessage style={{ width: '18.66px', height: '18.66px'}} />
//         </button>
//         <button>
//           <IoClose style={{ width: '18px', height: '18px'}} />
//         </button>
//       </div>

//       <div className={style.videoContainer}>
//         { !isConnected ? (
//           <>
//           <div>
//             <h2> {channelId} </h2>
//             <div className={style.mediaDeviceContainer}>
//               <button style={{backgroundColor: 'Green'}} onClick={joinSession}> <IoCall /> </button>
//             </div>
//           </div>
//           </>
//         ) : (
//           <>
//           <div>
//             {publisher !== undefined ? (
//                 <div onClick={() => handleMainVideoStream(publisher)}>
//                     <UserVideoComponent
//                         streamManager={publisher} />
//                 </div>
//             ) : null}
//             {subscribers.map((sub, i) => (
//                 <div key={sub.id} onClick={() => handleMainVideoStream(sub)}>
//                     <span>{sub.id}</span>
//                     <UserVideoComponent streamManager={sub} />
//                 </div>
//             ))}
//             <div className={style.mediaDeviceContainer}>
//               <button onClick={() => setScreenCam(!screenCam)}>
//                 { screenCam ? (<LuMonitorOff />) : (<LuMonitor />) }
//               </button>
//               <button onClick={toggleCamera}>
//                 { isCameraConnected ? (<IoVideocamOffOutline />) : (<IoVideocamOutline />)} 
//               </button>
//               <button onClick={toggleMice}> 
//                 { isMiceConnected ? (<MdOutlineKeyboardVoice />) : (<MdKeyboardVoice />)} 
//               </button>
//                 <button style={{backgroundColor: 'Red'}} onClick={leaveSession}> <IoCallOutline /> </button>
//             </div>
//           </div>
//         </>
//         )}
//       </div>
    

//       {/* <div className={style.mediaDeviceContainer}>
//         <button onClick={() => setScreenCam(!screenCam)}>
//           { screenCam ? (<LuMonitorOff />) : (<LuMonitor />) }
//         </button>
//         <button onClick={toggleCamera}>
//           { isCameraConnected ? (<IoVideocamOffOutline />) : (<IoVideocamOutline />)} 
//         </button>
//         <button onClick={toggleMice}> 
//           { isMiceConnected ? (<MdOutlineKeyboardVoice />) : (<MdKeyboardVoice />)} 
//         </button>
//         { isConnected ? (
//           <button style={{backgroundColor: 'Red'}} onClick={leaveSession}> <IoCallOutline /> </button>
//         ) : (
//           <button style={{backgroundColor: 'Green'}} onClick={joinSession}> <IoCall /> </button>
//         )} 
//       </div> */}
//     </div>
//     </>
//   )
// };

// export default MediaCall;

