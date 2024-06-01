import React, { useState, useEffect } from "react";
import DmMessageSender from "./MessageSender/MessageSender";
import useWebSocketStore from "../../../../actions/useWebSocketStore";
import API from "../../../../utils/API/API";

//웹소켓에 연결된 후 지정된 경로로 구독(subscribe)
//dm은 roomId 넘겨받아야 함.
export default function ChatModal({
  userId,
  writer,
  onNewMessage,
  client,
  roomId,
}) {
  const [chatList, setChatList] = useState([]);
  const [typingUsers, setTypingUsers] = useState([]);
  const { sendMessage } = useWebSocketStore((state) => ({
    sendMessage: state.sendMessage,
  }));

  useEffect(() => {
    if (client) {
      client.onConnect = () => {
        //dm구독
        client.subscribe(API.SUBSCRIBE_DM(roomId), (frame) => {
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
            console.error("DM 구독 오류", error);
          }
        });
      };
    }
  }, [client, userId, onNewMessage, roomId]);

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
    sendMessage(API.SEND_DM, messageBody);
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

      <DmMessageSender
        onMessageSend={handleSendMessage}
        writer={writer}
        client={client}
        roomId={roomId}
      />
    </div>
  );
}
