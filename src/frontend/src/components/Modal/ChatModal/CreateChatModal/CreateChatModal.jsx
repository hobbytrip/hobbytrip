import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";

import MessageSender from "./MessageSender/MessageSender";

export default function ChatModal({ userId, onNewMessage }) {
  const [client, setClient] = useState(null);
  const [chatList, setChatList] = useState([]); // 채팅 기록

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

  const sendMessage = (messageContent) => {
    const messageBody = {
      serverId: serverId,
      channelId: channelId,
      userId: userId,
      parentId: 0,
      profileImage: "ho",
      writer: "테스트유저",
      content: messageContent,
      fileUrl: uploadedFileUrl, // 파일 업로드된 URL도 메시지에 포함
    };
    console.error("Message Body:", messageBody);
    client.publish({
      destination: "/ws/api/chat/server/message/send",
      body: JSON.stringify(messageBody),
    });
    setChatList((prevChatList) => [...prevChatList, messageBody]);
    onNewMessage(messageBody);
    setUploadedFileUrl(null); // 파일url 초기화
  };

  return (
    <div>
      <MessageSender onMessageSend={sendMessage} />
    </div>
  );
}
