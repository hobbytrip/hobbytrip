import React from "react";
import s from "./ChatList.module.css";

const ChatList = ({ userId, chatList }) => {
  return (
    <div className={s.wrapper}>
      {chatList.map((message, index) => (
        <div key={index} className={s.chatBox}>
          {message.userId !== userId
            ? `${message.writer}: ${message.content}`
            : `나: ${message.content}`}
        </div>
      ))}
    </div>
  );
};

export default ChatList;
