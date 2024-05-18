import React from "react";

const ChatList = ({ userId, chatList }) => {
  return (
    <div>
      <ul>
        {chatList.map((message, index) => (
          <li key={index}>
            <strong>
              {message.userId !== userId
                ? `${message.writer}: ${message.content}`
                : `나: ${message.content}`}
            </strong>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ChatList;
