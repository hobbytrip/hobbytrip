import React, { useState, useEffect } from "react";
import { IoSend } from "react-icons/io5";
import s from "./CreateChatModal.module.css";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { useParams } from "react-router-dom";

export default function ChatModal({ userId, onNewMessage }) {
  const [client, setClient] = useState(null);
  const [chatList, setChatList] = useState([]); //채팅 기록
  const [chatMessage, setChatMessage] = useState("");
  const { serverId, channelId } = useParams();

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
          return new SockJS("http://localhost:7070/ws-stomp");
        },
        debug: function (str) {
          console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        connectHeaders: {
          userId: userId,
        },
      });

      stompClient.onConnect = () => {
        console.log("웹소켓 연결 open되었습니다.");
        stompClient.subscribe(`/topic/server/${serverId}`, (frame) => {
          try {
            const parsedMessage = JSON.parse(frame.body);
            setChatList((prevMsgs) => [...prevMsgs, parsedMessage]);
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
    if (chatMessage === "") {
      return;
    }
    if (client && client.connected) {
      const messageBody = {
        serverId: serverId,
        channelId: channelId,
        userId: userId,
        parentId: 0,
        profileImage: "ho",
        writer: "테스트유저",
        content: chatMessage,
      };
      console.error("Message Body:", messageBody);
      client.publish({
        destination: "/ws/api/chat/server/message/send",
        body: JSON.stringify(messageBody),
      });
      setChatList((prevChatList) => [...prevChatList, messageBody]);
      onNewMessage(messageBody);
      setChatMessage("");
    }
  };

  return (
    <div className={s.wrapper}>
      <div className={s.inputContainer}>
        <div className={s.inputBox}>
          <input
            onSubmit={(e) => e.preventDefault()}
            type="text"
            id="message"
            value={chatMessage}
            className={s.inputContent}
            placeholder="메세지 보내기"
            onChange={(e) => setChatMessage(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                sendMessage();
              }
            }}
          />
        </div>
        <IoSend className={s.sendBtn} onClick={sendMessage} />
      </div>
    </div>
  );
}
