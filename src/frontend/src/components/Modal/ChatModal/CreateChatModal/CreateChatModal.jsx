import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";
import MessageSender from "./MessageSender/MessageSender";

export default function ChatModal({ userId, writer, onNewMessage }) {
  const [client, setClient] = useState(null);
  const [chatList, setChatList] = useState([]); // 채팅 기록
  const [typingUsers, setTypingUsers] = useState([]); // 타이핑 중인 사용자들
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
        webSocketFactory: () => new SockJS("http://localhost:7070/ws-stomp"),
        debug: (str) => console.log(str),
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
            if (
              parsedMessage.actionType === "TYPING" &&
              parsedMessage.writer !== userId
            ) {
              // 타이핑 상태
              setTypingUsers((prev) => [...prev, parsedMessage.writer]);
              setTimeout(() => {
                setTypingUsers((prev) =>
                  prev.filter((user) => user !== parsedMessage.writer)
                );
              }, 2000);
            } else {
              setChatList((prevMsgs) => [...prevMsgs, parsedMessage]);
            }
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

  const sendMessage = (messageContent, uploadedFileUrl) => {
    const messageBody = {
      serverId: serverId,
      channelId: channelId,
      userId: userId,
      parentId: 0,
      profileImage: "ho",
      writer: writer,
      content: messageContent,
    };
    if (uploadedFileUrl) {
      messageBody.fileUrl = uploadedFileUrl;
    }
    console.error("Message Body:", messageBody);
    client.publish({
      destination: "/ws/api/chat/server/message/send",
      body: JSON.stringify(messageBody),
    });
    setChatList((prevChatList) => [...prevChatList, messageBody]);
    onNewMessage(messageBody);
  };

  return (
    <div>
      {typingUsers.length > 0 && (
        <div className={s.typingIndicator}>
          {typingUsers.join(", ")} 타이핑 중...
        </div>
      )}
      <MessageSender
        onMessageSend={sendMessage}
        serverId={serverId}
        channelId={channelId}
        writer={writer}
        client={client}
      />
    </div>
  );
}
