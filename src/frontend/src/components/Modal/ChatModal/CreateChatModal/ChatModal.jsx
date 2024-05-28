import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import MessageSender from "./MessageSender/MessageSender";
import useWebSocketStore from "../../../../actions/useWebSocketStore";

//웹소켓에 연결된 후 지정된 경로로 구독(subscribe)
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
        client.subscribe(`/topic/server/${serverId}`, (frame) => {
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
                prevMsgs.filter(
                  (msg) => msg.messageId !== parsedMessage.messageId
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

  const handleSendMessage = (messageContent, uploadedFile) => {
    const messageBody = {
      serverId,
      channelId,
      userId,
      parentId: 0,
      profileImage: "ho",
      writer,
      content: messageContent,
      createdAt: new Date().toISOString(), //현재시간
    };
    if (uploadedFile) {
      messageBody.files = [uploadedFile];
    }
    sendMessage("/ws/api/chat/server/message/send", messageBody);
    setChatList((prevChatList) => [...prevChatList, messageBody]);
    if (onNewMessage) {
      onNewMessage(messageBody);
    }
  };

  return (
    <div>
      {/* {chatList.map((message) => (
        <ChatMessage
          key={message.messageId}
          message={message}
          onModifyMessage={handleModifyMessage}
          onDeleteMessage={handleDeleteMessage}
        />
      ))} */}
      {typingUsers.length > 0 && (
        <div className="typingIndicator">
          {typingUsers.length >= 5
            ? "Multiple users are typing..."
            : `${typingUsers.join(", ")} is typing...`}
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
