import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { IoChatbubbleEllipses } from "react-icons/io5";
import axios from "axios";
import { axiosInstance } from "../../../utils/axiosInstance";
import s from "./ChatRoom.module.css";
import TopHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatRoomInfo from "../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo";
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar";
import ChatModal from "../../../components/Modal/ChatModal/CreateChatModal/ChatModal";
import ChatMessage from "../../../components/Modal/ChatModal/ChatMessage/ChatMessage";
import ChatChannelType from "../../../components/Modal/ChatModal/ChatChannelType/ChatChannelType";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
import useWebSocketStore from "../../../actions/useWebSocketStore";
import API from "../../../utils/API/TEST_API";
import useUserStore from "../../../actions/useUserStore";

const fetchChatHistory = async ({ queryKey }) => {
  const [_, channelId, page] = queryKey;
  const token = localStorage.getItem("accessToken");
  try {
    const response = await axios.get(API.GET_HISTORY, {
      params: {
        channelId: channelId,
        page,
        size: 20,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
      withCredentials: true,
    });
    const responseData = response.data.data;
    if (responseData.length === 0) {
      console.error("빈 데이터");
    }
    return responseData.data.reverse();
  } catch (err) {
    console.error("채팅기록 불러오기 오류", err);
    throw new Error("채팅기록 불러오기 오류");
  }
};

const postUserLocation = async (userId, serverId, channelId) => {
  try {
    await axiosInstance.post(API.POST_LOCATION, {
      userId: userId,
      serverId: serverId,
      channelId: channelId,
    });
  } catch (err) {
    console.error("사용자 위치 POST 실패", err);
    throw new Error("사용자 위치 POST 실패");
  }
};

function ChatRoom() {
  const { userId } = useUserStore();
  const { serverId, channelId } = useParams();
  const [chatList, setChatList] = useState([]);
  const [page, setPage] = useState(0);
  const chatListContainerRef = useRef(null);
  const { client } = useWebSocketStore();

  const { data, error, isLoading, refetch } = useQuery({
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
  }, [userId, serverId, channelId]);

  const handleNewMessage = (newMessage) => {
    setChatList((prevChatList) => [...prevChatList, newMessage]);
    if (chatListContainerRef.current) {
      chatListContainerRef.current.scrollTop =
        chatListContainerRef.current.scrollHeight;
    }
  };

  const handleModifyMessage = (messageId, newContent) => {
    client.publish({
      destination: "/ws/api/chat/server/message/modify",
      body: JSON.stringify({
        serverId: serverId,
        messageId: messageId,
        content: newContent,
      }),
    });
    setChatList((prevMsgs) =>
      prevMsgs.map((msg) =>
        msg.messageId === messageId ? { ...msg, content: newContent } : msg
      )
    );
  };

  const handleDeleteMessage = (messageId) => {
    client.publish({
      destination: "/ws/api/chat/server/message/delete",
      body: JSON.stringify({
        serverId: serverId,
        messageId: messageId,
      }),
    });
    setChatList((prevMsgs) =>
      prevMsgs.filter((msg) => msg.messageId !== messageId)
    );
  };

  const updatePage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  const groupedMessages = groupMessagesByDate(chatList);

  if (isLoading && chatList.length === 0) return <div>로딩 중...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <div className={s.chatRoomWrapper}>
      <div className={s.wrapper}>
        <div className={s.topContainer}>
          <TopHeader />
          <ChatRoomInfo />
          <ChatSearchBar />
        </div>
        <div className={s.chatContainer}>
          <ChatChannelType />
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
            <InfiniteScrollComponent
              dataLength={chatList.length}
              next={updatePage}
              hasMore={true}
              scrollableTarget="chatListContainer"
            >
              {Object.keys(groupedMessages).map((date) => (
                <div key={date}>
                  <h4 className={s.dateHeader}>{date}</h4>
                  {groupedMessages[date].map((message, index) => (
                    <ChatMessage
                      key={index}
                      message={message}
                      onModifyMessage={handleModifyMessage}
                      onDeleteMessage={handleDeleteMessage}
                    />
                  ))}
                </div>
              ))}
            </InfiniteScrollComponent>
          </div>
          <div className={s.messageSender}>
            <ChatModal
              onNewMessage={handleNewMessage}
              client={client}
              fetchHistory={refetch}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

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

export default ChatRoom;
