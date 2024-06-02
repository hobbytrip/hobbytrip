import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { IoChatbubbleEllipses } from "react-icons/io5";
import axios from "axios";
import s from "./ChatRoom.module.css";
import TopHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader"; //로고있는 최상단 헤더
import ChatRoomInfo from "../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo"; //서버 로고, 이름
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar"; //검색창
import ChatModal from "../../../components/Modal/ChatModal/CreateChatModal/ChatModal";
import ChatMessage from "../../../components/Modal/ChatModal/ChatMessage/ChatMessage";
import ChatChannelType from "../../../components/Modal/ChatModal/ChatChannelType/ChatChannelType";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
import useWebSocketStore from "../../../actions/useWebSocketStore";
import API from "../../../utils/API/API";

const fetchChatHistory = async ({ queryKey }) => {
  const [_, channelId, page] = queryKey;
  try {
    const response = await axios.get(API.GET_HISTORY, {
      params: {
        channelId,
        page,
        size: 20,
      },
    });
    const responseData = response.data.data;
    return responseData.data.reverse();
  } catch (err) {
    console.error("채팅기록 불러오기 오류", err);
    throw new Error("채팅기록 불러오기 오류");
  }
};

const postUserLocation = async (userId, serverId, channelId) => {
  try {
    const response = await axios.post(API.POST_LOCATION, {
      userId,
      serverId,
      channelId,
    });
    console.log("User location:", response.data);
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
  const nickname = "테스트유저0528"; //임시 테스트용. userstore에서 가져와야 한다.

  const { client, connect, disconnect, modifyMessage, deleteMessage } =
    useWebSocketStore((state) => ({
      client: state.client,
      connect: state.connect,
      disconnect: state.disconnect,
      modifyMessage: state.modifyMessage,
      deleteMessage: state.deleteMessage,
    }));

  useEffect(() => {
    connect(userId);

    return () => {
      disconnect();
    };
  }, [userId, connect, disconnect]);

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
  }, [userId, serverId, channelId]);

  const handleNewMessage = (newMessage) => {
    setChatList((prevChatList) => [...prevChatList, newMessage]);
    if (chatListContainerRef.current) {
      chatListContainerRef.current.scrollTop =
        chatListContainerRef.current.scrollHeight;
    }
  };

  const handleModifyMessage = (messageId, newContent) => {
    modifyMessage(API.MODIFY_CHAT, {
      serverId: serverId,
      messageId: messageId,
      content: newContent,
    });
    setChatList((prevMsgs) =>
      prevMsgs.map((msg) =>
        msg.messageId === messageId ? { ...msg, content: newContent } : msg
      )
    );
  };

  const handleDeleteMessage = (messageId) => {
    deleteMessage(API.DELETE_CHAT, {
      serverId: serverId,
      messageId: messageId,
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
            userId={userId}
            writer={nickname}
            onNewMessage={handleNewMessage}
            client={client}
          />
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