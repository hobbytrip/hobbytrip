import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import MessageSender from "../CreateChatModal/MessageSender/MessageSender";
import useWebSocketStore from "../../../../actions/useWebSocketStore";
import API from "../../../../utils/API/API";
import axios from "axios";

export default function ChatModal({ userId, writer, onNewMessage, client }) {
  const { serverId, channelId } = useParams();
  const [chatList, setChatList] = useState([]);
  const [typingUsers, setTypingUsers] = useState([]);
  const [file, setFile] = useState(null);

  useEffect(() => {
    if (client) {
      client.onConnect = () => {
        client.subscribe(API.SUBSCRIBE_CHAT(serverId), (frame) => {
          try {
            const parsedMessage = JSON.parse(frame.body);

            if (
              parsedMessage.actionType === "TYPING" &&
              parsedMessage.userId !== userId
            ) {
              setTypingUsers((prevTypingUsers) => {
                if (!prevTypingUsers.includes(parsedMessage.writer)) {
                  return [...prevTypingUsers, parsedMessage.writer];
                }
                return prevTypingUsers;
              });
            } else if (parsedMessage.actionType === "STOP_TYPING") {
              setTypingUsers((prevTypingUsers) =>
                prevTypingUsers.filter(
                  (username) => username !== parsedMessage.writer
                )
              );
            } else if (
              parsedMessage.actionType === "SEND" &&
              parsedMessage.userId !== userId
            ) {
              setChatList((prevChatList) => [...prevChatList, parsedMessage]);
              if (onNewMessage) {
                onNewMessage(parsedMessage);
              }
            } else if (
              parsedMessage.actionType === "SEND" &&
              parsedMessage.files
            ) {
              setChatList((prevChatList) => [...prevChatList, parsedMessage]);
              if (onNewMessage) {
                onNewMessage(parsedMessage);
              }
              setFile(parsedMessage.files[0]);
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
      createdAt: new Date().toISOString(),
    };
    try {
      const formData = new FormData();
      const jsonMsg = JSON.stringify(messageBody);
      const req = new Blob([jsonMsg], { type: "application/json" });
      formData.append("createRequest", req);
      if (uploadedFile) {
        formData.append("files", uploadedFile);
        await axios.post(API.FILE_UPLOAD, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
      }
      setChatList((prevChatList) => [...prevChatList, messageBody]);
      if (onNewMessage) {
        onNewMessage(messageBody);
      }
    } catch (error) {
      console.error("메시지 전송 오류:", error);
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
      {file && (
        <div>
          <p>Received file: {file.fileUrl}</p>
          <button onClick={() => window.open(file.fileUrl, "_blank")}>
            Download File
          </button>
        </div>
      )}

      <MessageSender
        onMessageSend={handleSendMessage}
        serverId={serverId}
        channelId={channelId}
        writer={writer}
        client={client}
      />
    </div>
  );
}
