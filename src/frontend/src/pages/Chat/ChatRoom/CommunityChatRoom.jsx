import React from "react";
import ChatRoomWrapper from "../../../components/Common/ChatRoom/Wrapper/ChatRoomWrapper";
import MessageList from "../../../components/Common/ChatRoom/MessageList/MessageList";
import MessageSender from "../../../components/Common/ChatRoom/MessageSender/MessageSender";
import ChatChannelType from "../../../components/Modal/ChatModal/ChatChannelType/ChatChannelType";
import useChat from "../../../hooks/useChat";
import useUserStore from "../../../actions/useUserStore";
import { useParams } from "react-router-dom";

const CommunityChatRoom = () => {
  const { userId, nickname } = useUserStore();
  const { serverId, channelId } = useParams();
  const {
    chatList,
    isLoading,
    error,
    handleSendMessage,
    handleModifyMessage,
    handleDeleteMessage,
  } = useChat(serverId, channelId, userId);

  if (isLoading && chatList.length === 0) return <div>로딩 중...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <ChatRoomWrapper>
      <ChatChannelType />
      <MessageList
        messages={chatList}
        onModifyMessage={handleModifyMessage}
        onDeleteMessage={handleDeleteMessage}
      />
      <div className="messageSender">
        <MessageSender
          onMessageSend={handleSendMessage}
          serverId={serverId}
          channelId={channelId}
          writer={nickname}
        />
      </div>
    </ChatRoomWrapper>
  );
};

export default CommunityChatRoom;
