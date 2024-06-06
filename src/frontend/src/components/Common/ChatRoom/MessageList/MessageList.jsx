import React, { useRef, useEffect } from "react";
import { IoChatbubbleEllipses } from "react-icons/io5";
import s from "./MessageList.module.css";
import ChatMessage from "../ChatMessage/ChatMessage";

const MessageList = ({ messages, onModifyMessage, onDeleteMessage }) => {
  const groupedMessages = groupMessagesByDate(messages);
  const chatListContainerRef = useRef(null);

  useEffect(() => {
    if (chatListContainerRef.current) {
      chatListContainerRef.current.scrollTop =
        chatListContainerRef.current.scrollHeight;
    }
  }, [messages]);

  return (
    <div
      ref={chatListContainerRef}
      id="chatListContainer"
      className={s.chatListContainer}
      style={{ overflowY: "auto", height: "530px" }}
    >
      <div className={s.topInfos}>
        <IoChatbubbleEllipses className={s.chatIcon} />
        <h1>일반 채널에 오신 것을 환영합니다</h1>
      </div>
      {Object.keys(groupedMessages).map((date) => (
        <div key={date}>
          <h4 className={s.dateHeader}>{date}</h4>
          {groupedMessages[date].map((message, index) => (
            <ChatMessage
              key={index}
              message={message}
              onModifyMessage={onModifyMessage}
              onDeleteMessage={onDeleteMessage}
            />
          ))}
        </div>
      ))}
    </div>
  );
};

const groupMessagesByDate = (messages) => {
  const groupedMessages = {};

  messages.forEach((message) => {
    const date = new Date(message.createdAt);
    const dateString = `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`;

    if (!groupedMessages[dateString]) {
      groupedMessages[dateString] = [];
    }
    groupedMessages[dateString].push(message);
  });

  return groupedMessages;
};

export default MessageList;
