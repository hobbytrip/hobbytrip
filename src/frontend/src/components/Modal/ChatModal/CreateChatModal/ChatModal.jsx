import React, { useState, useEffect } from "react";
import { json, useParams } from "react-router-dom";
import MessageSender from "../CreateChatModal/MessageSender/MessageSender";
import useWebSocketStore from "../../../../actions/useWebSocketStore";
import API from "../../../../utils/API/API";
import axios from "axios";

export default function ChatModal({ userId, writer, onNewMessage, client }) {
  const { serverId, channelId } = useParams();
  const [chatList, setChatList] = useState([]);
  const [typingUsers, setTypingUsers] = useState([]);
  const { sendMessage } = useWebSocketStore((state) => ({
    sendMessage: state.sendMessage,
  }));

  useEffect(() => {
    if (client) {
      client.onConnect = () => {
        client.subscribe(API.SUBSCRIBE_CHAT(serverId), (frame) => {
          try {
            const parsedMessage = JSON.parse(frame.body);
            if (
              parsedMessage.actionType === "TYPING" &&
              parsedMessage.writer !== userId
            ) {
              setTypingUsers((prev) => [...prev, parsedMessage.writer]);
              setTimeout(() => {
                setTypingUsers((prev) =>
                  prev.filter((user) => user !== parsedMessage.writer)
                );
              }, 2000);
            } else if (parsedMessage.actionType === "SEND") {
              setChatList((prevMsgs) => [...prevMsgs, parsedMessage]);
              if (onNewMessage) {
                onNewMessage(parsedMessage);
              }
            } else if (parsedMessage.actionType === "MODIFY") {
              setChatList((prevMsgs) =>
                prevMsgs.map((msg) =>
                  msg.messageId === parsedMessage.messageId
                    ? { ...msg, content: parsedMessage.content }
                    : msg
                )
              );
            } else if (parsedMessage.actionType === "DELETE") {
              setChatList((prevMsgs) =>
                prevMsgs.map((msg) =>
                  msg.messageId === parsedMessage.messageId
                    ? { ...msg, deleted: true }
                    : msg
                )
              );
            }
          } catch (error) {
            console.error("구독 오류", error);
          }
        });
      };
    }
  }, [client, serverId, userId, onNewMessage]);

  const handleSendMessage = async (messageContent, uploadedFile) => {
    const messageBody = {
      serverId,
      channelId,
      userId,
      parentId: 0,
      profileImage: "ho",
      writer,
      content: messageContent,
      createdAt: new Date().toISOString(), // 현재시간
    };

    let newMessage = messageBody;

    if (uploadedFile !== null) {
      const formData = new FormData();
      const jsonMsg = JSON.stringify(messageBody);
      const req = new Blob([jsonMsg], { type: "application/json" });
      formData.append("createRequest", req);
      formData.append("files", uploadedFile);
      const response = await axios.post(API.FILE_UPLOAD, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
    }

    sendMessage(API.SEND_CHAT, newMessage);
    setChatList((prevChatList) => [...prevChatList, newMessage]);
    if (onNewMessage) {
      onNewMessage(newMessage);
    }
  };

  return (
    <div>
      {typingUsers.length > 0 && (
        <div className="typingIndicator">
          {typingUsers.length >= 5
            ? "여러 사용자가 입력 중입니다..."
            : `${typingUsers.join(", ")} 입력 중입니다...`}
        </div>
      )}

      <MessageSender
        onMessageSend={handleSendMessage}
        // onFileMessageSend={handleFileMessageSend}
        serverId={serverId}
        channelId={channelId}
        writer={writer}
        client={client}
      />
    </div>
  );
}
