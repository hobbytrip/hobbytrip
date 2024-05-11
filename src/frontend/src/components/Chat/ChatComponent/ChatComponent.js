import React, { useState, useEffect } from "react";
import axios from "axios";
import ChatInput from "./ChatInput";
import ChatMessage from "./ChatMessage";

const ChatComponent = ({ serverId, channelId, userId }) => {
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    const fetchChatMessages = async () => {
      try {
        const response = await axios.get(
          `/api/chat/server/messages/channel?channelId=${channelId}&page=0&size=20`
        );
        setMessages(response.data);
      } catch (error) {
        console.error("Error fetching messages:", error);
      }
    };

    fetchChatMessages();
  }, [channelId]);

  return (
    <div>
      <h1>Chat</h1>
      <div>
        {messages.map((message) => (
          <ChatMessage key={message.messageId} message={message} />
        ))}
      </div>
      <ChatInput serverId={serverId} channelId={channelId} userId={userId} />
    </div>
  );
};

export default ChatComponent;
