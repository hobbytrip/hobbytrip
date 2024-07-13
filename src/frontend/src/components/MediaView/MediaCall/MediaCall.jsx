import { OpenVidu } from "openvidu-browser";
import { useParams, useNavigate } from "react-router-dom";
import React, { useCallback, useEffect, useRef, useState } from "react";
import style from "./MediaCall.module.css";
import axios from "axios";

import UserVideoComponent from "./../UserVideoComponent";
import useUserStore from "../../../actions/useUserStore";
import API from "../../../utils/API/API";

import { HiMiniSpeakerWave } from "react-icons/hi2";
import { AiFillMessage } from "react-icons/ai";
import {
  IoClose,
  IoVideocamOutline,
  IoVideocamOffOutline,
  IoCall,
  IoCallOutline,
} from "react-icons/io5";
import { LuMonitor, LuMonitorOff } from "react-icons/lu";
import { MdOutlineKeyboardVoice, MdKeyboardVoice } from "react-icons/md";

export default function MediaCall() {
  const [session, setSession] = useState(undefined);
  const [publisher, setPublisher] = useState(undefined);
  const [subscribers, setSubscribers] = useState([]);
  const [isScreenConnected, setIsScreenConnected] = useState(false);
  const [isCameraConnected, setIsCameraConnected] = useState(false);
  const [isMicConnected, setIsMicConnected] = useState(false);

  const { serverId, channelId } = useParams();
  const newSessionId = `${serverId}${channelId}`;
  const { USER } = useUserStore();
  const userId = USER.userId;
  const nav = useNavigate();

  const accessToken = localStorage.getItem("accessToken");
  const OV = useRef(null);

  useEffect(() => {
    OV.current = new OpenVidu(); 

    return () => {
      if (OV.current) {
        OV.current = null;
      }
    };
  }, []);

  const joinSession = useCallback((e) => {
    e.preventDefault();

    const mySession = OV.current.initSession();

    mySession.on("streamCreated", (event) => {
      const subscriber = mySession.subscribe(event.stream, undefined);
      setSubscribers((subscribers) => [...subscribers, subscriber]);
    });

    mySession.on("streamDestroyed", (event) => {
      const streamManager = event.stream.streamManager;
      setSubscribers((prevSubscribers) => {
        return prevSubscribers.filter(
          (subscriber) => subscriber !== streamManager
        );
      });
    });

    mySession.on("streamPlaying", (event) => {
      console.log("Stream is playing for:", event.stream.streamId);
    });

    setSession(mySession);
  }, []);

  useEffect(() => {
    if (session) {
      getToken().then(async (token) => {
        console.log(token);
        try {
          await session.connect(token);
          setIsCameraConnected(false);
          setIsMicConnected(false);
          setIsScreenConnected(false);
        } catch (error) {
          console.log(error);
        }
      });
    }
  }, [session]);

  const getToken = useCallback(async () => {
    return createSession(newSessionId).then((sessionId) =>
      createToken(sessionId)
    );
  }, [newSessionId]);

  const createSession = async (sessionId) => {
    const data = {
      customSessionId: sessionId,
      userId: userId,
      serverId: serverId,
      channelId: channelId,
    };
    console.log(JSON.stringify(data));
    const res = await axios.post(
      API.MEDIA,
      { customSessionId: sessionId }, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
        body: data,
        withCredentials: true,
      }
    );
    return res.data;
  };

  const createToken = async (sessionId) => {
    console.log(`${API.MEDIA}/${sessionId}/connections`);
    const res = await axios.post(
      `${API.MEDIA}/${sessionId}/connections`,
      {
        userId: userId,
        channelId: channelId,
        serverId: serverId,
      },
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    console.log(res.data);
    return res.data;
  };

  const leaveSession = useCallback(() => {
    if (session) {
      session.disconnect();
      const data = JSON.stringify({
        userId: userId,
        serverId: serverId,
        channelId: channelId,
      });
      console.log(data, accessToken);
      axios.delete(`${API.MEDIA}/${newSessionId}/disconnect`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        data: {
          userId: userId,
          channelId: channelId,
          serverId: serverId,
        },
      });
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
    window.addEventListener("beforeunload", handleBeforeUnload);

    return () => {
      window.removeEventListener("beforeunload", handleBeforeUnload);
    };
  }, [leaveSession]);
  
  const toggleCamera = async () => {
    if (publisher) {
      session.unpublish(publisher);
      setPublisher(null);
      setIsCameraConnected(false);
      setIsScreenConnected(false);
    } else {
      const cameraPublisher = await OV.current.initPublisherAsync(undefined, {
        videoSource: undefined,
        publishAudio: true,
        publishVideo: true,
        mirror: false,
      });
      session.publish(cameraPublisher);
      setPublisher(cameraPublisher);
      setIsCameraConnected(true);
      setIsScreenConnected(false);
    }
  };
  
  const toggleScreenShare = async () => {
    if (isScreenConnected) {
      session.unpublish(publisher);
      setPublisher(null);
      setIsScreenConnected(false); 
    } else {
      if (publisher) {
        session.unpublish(publisher);
        setPublisher(null);
        setIsCameraConnected(false); 
      }
  
      const screenPublisher = await OV.current.initPublisherAsync(undefined, {
        videoSource: "screen",
        publishAudio: true,
        publishVideo: true,
        mirror: false,
      });
      session.publish(screenPublisher);
      setPublisher(screenPublisher);
      setIsScreenConnected(true); 
    }
  };
    
  const toggleMic = () => {
    if (publisher) {
      const publishAudio = publisher.stream.audioActive;
      publisher.publishAudio(!publishAudio);
      setIsMicConnected(!publishAudio);
    }
  };

  const handleClose = () => {
    nav(-1);
  };

  return (
    <>
      <div className={style.wrapper}>
        <div className={style.container}>
          <div className={style.headerContainer}>
            <HiMiniSpeakerWave style={{ width: "15px", height: "15px" }} />
            <h4> {channelId} </h4>
            <button>
              <AiFillMessage style={{ width: "18.66px", height: "18.66px" }} />
            </button>
            <button>
              <IoClose
                style={{ width: "18px", height: "18px" }}
                onClick={handleClose}
              />
            </button>
          </div>

          <div className={style.mediaContainer}>
            {session === undefined ? (
              <>
                <div className={style.videoContainer}>
                  <h3> 통화에 참여하시겠습니까? </h3>
                </div>
                <div className={style.mediaDeviceContainer}>
                  <button
                    style={{ backgroundColor: "Green" }}
                    onClick={joinSession}
                  >
                    {" "}
                    <IoCall />{" "}
                  </button>
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
                  <div key={i}>
                    <UserVideoComponent streamManager={sub} />
                  </div>
                ))}
                <div className={style.mediaDeviceContainer}>
                  <button onClick={toggleScreenShare}>
                    {isScreenConnected ? <LuMonitorOff /> : <LuMonitor /> }
                  </button>
                  <button onClick={toggleCamera}>
                    {isCameraConnected ? (
                      <IoVideocamOffOutline />
                    ) : (
                      <IoVideocamOutline />
                    )}
                  </button>
                  <button onClick={toggleMic}>
                    {isMicConnected ? (
                      <MdOutlineKeyboardVoice />
                    ) : (
                      <MdKeyboardVoice />
                    )}
                  </button>
                  <button
                    style={{ backgroundColor: "Red" }}
                    onClick={leaveSession}
                  >
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
