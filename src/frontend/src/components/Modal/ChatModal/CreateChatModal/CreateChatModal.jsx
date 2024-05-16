import React, { useState, useEffect } from "react";
import s from "./CreateChatModal.module.css";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";
// import { useParams } from "react-router-dom";

export default function ChatModal({ username, serverId }) {
  const [client, setClient] = useState(null);
  const [chatMessage, setChatMessage] = useState("");
  // const param = useParams();
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
        stompClient.subscribe("/topic/server/1", (frame) => {
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
  useEffect(() => {
    console.log(username);
    connectChat();

    return () => {
      if (client && client.connected) {
        client.deactivate();
      }
    };
  }, [serverId]);

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
      <div className={s.inputContainer}>
        <div className={s.inputBox}>
          <input
            type="text"
            id="message"
            value={chatMessage}
            className={s.inputContent}
            onChange={(e) => setChatMessage(e.target.value)}
          />
        </div>
        <button onClick={sendMessage}>전송</button>
      </div>
    </div>
  );
}
