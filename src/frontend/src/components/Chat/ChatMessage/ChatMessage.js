// ChatMessage.js
import React from "react";
import useUserStore from "./useUserStore";

const ChatMessage = ({ message }) => {
  const user = useUserStore((state) => state.user);
  return (
    <div>
      <img src={message.profileImage} alt="Profile" />
      <div>
        //일치할때 '나' 표시
        <p>{message.writer === user.name ? "나" : message.writer}</p>
        <p>{message.content}</p>
      </div>
    </div>
  );
};

export default ChatMessage;
