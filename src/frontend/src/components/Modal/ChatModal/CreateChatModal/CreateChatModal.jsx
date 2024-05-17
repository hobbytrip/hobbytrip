import React, { useState, useEffect } from "react";
import { IoSend } from "react-icons/io5";
import s from "./CreateChatModal.module.css";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";
import ChatHeader from "../../../Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatHeaderModal from "../ChatHeaderModal/ChatHeaderModal";
import { useParams } from "react-router-dom";
import ChatSearchBar from "../ChatSearchBar/ChatSearchBar";
import ChatChannelInfo from "../ChatChannelInfo/ChatChannelInfo";

export default function ChatModal({ username }) {
  const [client, setClient] = useState(null);
  const [chatMessage, setChatMessage] = useState("");
  const { serverId } = useParams();
  // const serverId = 1;

  useEffect(() => {
    connectChat();
    return () => {
      if (client && client.connected) {
        client.deactivate();
      }
    };
  }, []);

  const connectChat = async () => {
    try {
      const stompClient = new StompJs.Client({
        webSocketFactory: function () {
          return new SockJS("http://localhost:7000/ws-stomp");
        },
        debug: function (str) {
          console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });

      stompClient.onConnect = () => {
        console.log("웹소켓 연결 open되었습니다.");
        stompClient.subscribe(`/topic/server/${serverId}`, (frame) => {
          try {
            const parsedMessage = JSON.parse(frame.body);
            console.log("parsedMessage", parsedMessage);
            setChatMessage((prevMsg) => [...prevMsg, parsedMessage]);
          } catch (error) {
            console.log("구독 오류 발생");
          }
        });
      };

      stompClient.activate();
      setClient(stompClient);
    } catch (err) {
      console.log(err);
    }
  };

  const sendMessage = () => {
    if (client && client.connected) {
      const messageBody = {
        serverId: serverId,
        channelId: 4,
        userId: 20,
        parentId: 0,
        profileImage: "ho",
        type: "writer-send",
        writer: username,
        content: chatMessage,
      };
      console.log("Message Body:", messageBody);
      client.publish({
        destination: "/ws/api/chat/server/message/send",
        body: JSON.stringify(messageBody),
      });
      setChatMessage("");
    }
  };

  return (
    <div className={s.wrapper}>
      <div className={s.topContainer}>
        <ChatHeader />
        <ChatHeaderModal />
        <ChatSearchBar />
      </div>
      <div className={s.chatContainer}>
        <ChatChannelInfo />
        <div className={s.chatListContainer}>채팅리스트</div>
      </div>
      <div className={s.inputContainer}>
        <div className={s.inputBox}>
          <input
            type="text"
            id="message"
            value={chatMessage}
            className={s.inputContent}
            placeholder="메세지 보내기"
            onChange={(e) => setChatMessage(e.target.value)}
          />
        </div>
        <IoSend className={s.sendBtn} onClick={sendMessage} />
      </div>
    </div>
  );
}
