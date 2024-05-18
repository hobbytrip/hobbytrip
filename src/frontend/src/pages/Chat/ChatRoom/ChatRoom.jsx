import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import s from "./ChatRoom.module.css";
import ChatHeaderModal from "../../../components/Modal/ChatModal/ChatHeaderModal/ChatHeaderModal";
import ChatHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar";
import CreateChatModal from "../../../components/Modal/ChatModal/CreateChatModal/CreateChatModal";
import ChatChannelInfo from "../../../components/Modal/ChatModal/ChatChannelInfo/ChatChannelInfo";
import ChatList from "../../../components/Modal/ChatModal/ChatList/ChatList";

const fetchChatHistory = async ({ queryKey }) => {
  const [_, channelId] = queryKey;
  try {
    const response = await axios.get(
      `http://localhost:7070/server/messages/channel`,
      {
        params: {
          channelId,
          page: 0,
          size: 20,
        },
      }
    );
    console.error("채팅 목록", response.data.data);
    return response.data.data;
  } catch (err) {
    console.error("채팅 목록 조회 실패");
    throw new Error("채팅 목록 조회 실패");
  }
};

function ChatRoom({ userId }) {
  const { channelId } = useParams();
  const [chatList, setChatList] = useState([]);

  const { data, error, isLoading } = useQuery({
    queryKey: ["messages", channelId],
    queryFn: fetchChatHistory,
    staleTime: 1000 * 60 * 5,
  });

  useEffect(() => {
    if (data && Array.isArray(data.data)) {
      setChatList(data.data);
    }
  }, [data]);

  const handleNewMessage = (newMessage) => {
    setChatList((prevChatList) => [...prevChatList, newMessage]);
  };

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <div className={s.wrapper}>
      <div className={s.topContainer}>
        <ChatHeader />
        <ChatHeaderModal />
        <ChatSearchBar />
      </div>
      <div className={s.chatContainer}>
        <ChatChannelInfo />
        <div className={s.chatListContainer}>
          {/* <ChatList userId={userId} chatList={chatList} /> */}
          <div>
            <ul>
              {chatList.map((message, index) => (
                <li key={index}>
                  <strong>
                    {message &&
                      message.userId !== undefined &&
                      message.content !== undefined &&
                      (message.userId !== userId
                        ? `${message.writer || "Unknown"}: ${message.content}`
                        : `나: ${message.content}`)}
                  </strong>
                </li>
              ))}
            </ul>
          </div>
        </div>
        <CreateChatModal
          userId={userId}
          chatList={chatList}
          setChatList={setChatList}
          onNewMessage={handleNewMessage}
        />
      </div>
    </div>
  );
}

export default ChatRoom;
