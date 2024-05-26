import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { IoChatbubbleEllipses } from "react-icons/io5";
import axios from "axios";
import s from "./ChatRoom.module.css";
import ChatHeaderModal from "../../../components/Modal/ChatModal/ChatHeaderModal/ChatHeaderModal";
import ChatHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar";
import CreateChatModal from "../../../components/Modal/ChatModal/CreateChatModal/CreateChatModal";
import ChatMessage from "../../../components/Modal/ChatModal/ChatMessage/ChatMessage";
import ChatChannelInfo from "../../../components/Modal/ChatModal/ChatChannelInfo/ChatChannelInfo";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";

const fetchChatHistory = async ({ queryKey }) => {
  const [_, channelId, page] = queryKey;
  try {
    const response = await axios.get(
      `http://localhost:7070/server/messages/channel`,
      {
        params: {
          channelId,
          page,
          size: 20,
        },
      }
    );
    const responseData = response.data.data;
    console.error("Fetched data:", responseData.data);
    console.error("Is data an array?", Array.isArray(responseData.data));
    return responseData.data.reverse();
  } catch (err) {
    console.error("채팅 목록 조회 실패", err);
    throw new Error("채팅 목록 조회 실패");
  }
};

const postUserLocation = async (userId, serverId, channelId) => {
  try {
    const response = await axios.post(
      `http://localhost:7070/server/user/location`,
      {
        userId,
        serverId,
        channelId,
      }
    );
    console.log("사용자 위치:", response.data);
  } catch (err) {
    console.error("사용자 위치 POST 실패", err);
    throw new Error("사용자 위치 POST 실패");
  }
};

function ChatRoom({ userId }) {
  const { serverId, channelId } = useParams();
  const [chatList, setChatList] = useState([]);
  const [page, setPage] = useState(0);
  const chatListContainerRef = useRef(null);

  const { data, error, isLoading } = useQuery({
    queryKey: ["messages", channelId, page],
    queryFn: fetchChatHistory,
    staleTime: 1000 * 60 * 5,
    keepPreviousData: true,
  });

  useEffect(() => {
    if (data && Array.isArray(data)) {
      setChatList((prevChatList) => [...data, ...prevChatList]);
    }
  }, [data]);

  useEffect(() => {
    if (page === 0 && chatListContainerRef.current) {
      chatListContainerRef.current.scrollTop =
        chatListContainerRef.current.scrollHeight;
    }
  }, [chatList, page]);

  useEffect(() => {
    postUserLocation(userId, serverId, channelId);
  }, [userId, channelId]); //userId와 채널 Id 바뀔 때마다

  const handleNewMessage = (newMessage) => {
    setChatList((prevChatList) => [...prevChatList, newMessage]);
    if (chatListContainerRef.current) {
      chatListContainerRef.current.scrollTop =
        chatListContainerRef.current.scrollHeight;
    }
  };

  const updatePage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  if (isLoading && chatList.length === 0) return <div>Loading...</div>;
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
        <div
          ref={chatListContainerRef}
          id="chatListContainer"
          className={s.chatListContainer}
          style={{ overflowY: "auto", height: "530px" }}
        >
          <div className={s.topInfos}>
            <IoChatbubbleEllipses className={s.chatIcon} />
            <h1>일반 채널에 오신 것을 환영합니다!</h1>
          </div>
          <InfiniteScrollComponent
            dataLength={chatList.length}
            next={updatePage}
            hasMore={true}
            scrollableTarget="chatListContainer"
          >
            {chatList.map((message, index) => (
              <ChatMessage key={index} message={message} />
            ))}
          </InfiniteScrollComponent>
        </div>
        <div className={s.messageSender}>
          <CreateChatModal
            userId={userId}
            chatList={chatList}
            setChatList={setChatList}
            onNewMessage={handleNewMessage}
          />
        </div>
      </div>
    </div>
  );
}

export default ChatRoom;
