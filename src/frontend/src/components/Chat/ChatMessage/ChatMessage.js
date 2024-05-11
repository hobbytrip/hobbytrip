import React from "react";

const ChatMessage = ({ message }) => {
  return (
    <div>
      <img src={message.profileImage} alt="Profile" />
      <div>
        <p>{message.writer}</p>
        <p>{message.content}</p>
      </div>
    </div>
  );
};

export default ChatMessage;
