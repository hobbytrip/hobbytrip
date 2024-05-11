import React, { useState } from "react";
import useAxios from "../../../utils/instance";

const ChatInput = ({ serverId, channelId, userId }) => {
  const axios = useAxios();
  const user = useUserStore((state) => state.user);
  const [content, setContent] = useState("");

  const sendMessage = async () => {
    try {
      const response = await axios.post("/ws/api/chat/server/message/send", {
        serverId,
        channelId,
        userId,
        parentId: 0, //채팅의 parentId:0, 댓글의 parentId: 댓글 개수
        profileImage: user.profileImage,
        type: "send",
        writer: user.name,
        content,
      });
      console.log(response.data);
    } catch (error) {
      console.error("Error sending message:", error);
    }
  };

  return (
    <div>
      <input
        type="text"
        value={content}
        onChange={(e) => setContent(e.target.value)}
      />
      <button onClick={sendMessage}>Send</button>
    </div>
  );
};

export default ChatInput;
