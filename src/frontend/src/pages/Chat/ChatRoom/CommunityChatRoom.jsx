import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { IoChatbubbleEllipses } from "react-icons/io5";
import s from "./ChatRoom.module.css";
import TopHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatRoomInfo from "../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo";
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar";
import MessageSender from "../../../components/Modal/ChatModal/CreateChatModal/MessageSender/MessageSender";
import ChatMessage from "../../../components/Modal/ChatModal/ChatMessage/ChatMessage";
import ChatChannelType from "../../../components/Modal/ChatModal/ChatChannelType/ChatChannelType";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
import useWebSocketStore from "../../../actions/useWebSocketStore";
import useChatStore from "../../../actions/useChatStore";
import API from "../../../utils/API/API";
import useUserStore from "../../../actions/useUserStore";
import useAuthStore from "../../../actions/useAuthStore";
import axios from "axios";
import { axiosInstance } from "../../../utils/axiosInstance";

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
  const { userId, nickname } = useUserStore();
  const { serverId, channelId } = useParams();
  const [page, setPage] = useState(0);
  const chatListContainerRef = useRef(null);
  const { accessToken } = useAuthStore();
  const { client, isConnected } = useWebSocketStore();
  const {
    typingUsers,
    setTypingUsers,
    deleteMessage,
    modifyMessage,
    sendMessage,
  } = useChatStore.getState();
  const { chatList } = useChatStore();

  const { data, error, isLoading } = useQuery({
    queryKey: ["messages", channelId, page],
    queryFn: fetchChatHistory,
    staleTime: 1000 * 60 * 5,
    keepPreviousData: true,
  });

  const connectWebSocket = (serverId) => {
    client.subscribe(API.SUBSCRIBE_CHAT(serverId), (frame) => {
      try {
        const parsedMessage = JSON.parse(frame.body);
        if (
          parsedMessage.actionType === "TYPING" &&
          parsedMessage.userId !== userId
        ) {
          setTypingUsers((prevTypingUsers) => {
            if (!prevTypingUsers.includes(parsedMessage.writer)) {
              return [...prevTypingUsers, parsedMessage.writer];
            }
            return prevTypingUsers;
          });
        } else if (parsedMessage.actionType === "STOP_TYPING") {
          setTypingUsers((prevTypingUsers) =>
            prevTypingUsers.filter(
              (username) => username !== parsedMessage.writer
            )
          );
        } else if (parsedMessage.actionType === "SEND") {
          sendMessage(parsedMessage);
          client.publish({
            destination: API.SEND_CHAT,
            body: JSON.stringify(parsedMessage),
          });
        } else if (parsedMessage.actionType === "MODIFY") {
          modifyMessage(parsedMessage.messageId, parsedMessage.content);
          client.publish({
            destination: API.MODIFY_CHAT,
            body: JSON.stringify(parsedMessage),
          });
        } else if (parsedMessage.actionType === "DELETE") {
          deleteMessage(parsedMessage.messageId);
          client.publish({
            destination: API.DELETE_CHAT,
            body: JSON.stringify(parsedMessage),
          });
        }
      } catch (error) {
        console.error("구독 오류", error);
      }
    });
  };

  const unsubscribeWebSocket = () => {
    client.unsubscribe(serverId);
  };

  useEffect(() => {
    postUserLocation(userId, serverId, channelId);
    console.log("웹소켓 연결여부", isConnected);
    if (client && isConnected) {
      unsubscribeWebSocket();
    }
    if (client) {
      connectWebSocket(serverId);
    }
  }, [serverId]);

  useEffect(() => {
    if (data && Array.isArray(data)) {
      if (Array.isArray(data).length === 0) {
        console.error("빈 채팅목록");
      } else {
        useChatStore.setState({ chatList: data });
        console.log(data);
      }
    }
  }, [data]);

  useEffect(() => {
    if (chatListContainerRef.current) {
      chatListContainerRef.current.scrollTop =
        chatListContainerRef.current.scrollHeight;
    }
  }, [chatList]);

  const handleSendMessage = async (messageContent, uploadedFiles) => {
    const messageBody = {
      serverId: serverId,
      channelId: channelId,
      userId: userId,
      parentId: 0,
      profileImage: "ho",
      writer: nickname,
      content: messageContent,
      createdAt: new Date().toISOString(),
      actionType: "SEND",
    };
    try {
      if (uploadedFiles && uploadedFiles.length > 0) {
        const formData = new FormData();
        const jsonMsg = JSON.stringify(messageBody);
        const req = new Blob([jsonMsg], { type: "application/json" });
        formData.append("createRequest", req);
        uploadedFiles.forEach((file) => {
          formData.append("files", file);
        });
        await axios.post(API.FILE_UPLOAD, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${accessToken}`,
          },
          withCredentials: true,
        });
      }
      sendMessage(messageBody);
      client.publish({
        destination: API.SEND_CHAT,
        body: JSON.stringify(messageBody),
      });
      // handleAction("SEND", messageBody);
    } catch (error) {
      console.error("메시지 전송 오류:", error);
    }
  };

  const handleModifyMessage = (messageId, newContent) => {
    const messageBody = {
      serverId: serverId,
      messageId: messageId,
      content: newContent,
      actionType: "MODIFY",
    };
    const modifiedMessage = chatList.find(
      (message) => message.messageId === messageId
    );
    if (modifiedMessage) {
      modifiedMessage.content = newContent;
      modifyMessage(messageId, newContent);
      client.publish({
        destination: API.MODIFY_CHAT,
        body: JSON.stringify(messageBody),
      });
    }
  };

  const handleDeleteMessage = (messageId) => {
    const messageBody = {
      serverId: serverId,
      messageId: messageId,
      actionType: "DELETE",
    };

    deleteMessage(messageId);
    client.publish({
      destination: API.DELETE_CHAT,
      body: JSON.stringify(messageBody),
    });
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
            {/* <InfiniteScrollComponent
              dataLength={chatList.length}
              next={updatePage}
              hasMore={true}
              scrollableTarget="chatListContainer"
            >
              
            </InfiniteScrollComponent> */}
          </div>
          <div className={s.messageSender}>
            {typingUsers.length > 0 && (
              <div className="typingIndicator">
                {typingUsers.length >= 5
                  ? "여러 사용자가 입력 중입니다..."
                  : `${typingUsers.join(", ")} 입력 중입니다...`}
              </div>
            )}
            <MessageSender
              onMessageSend={handleSendMessage}
              serverId={serverId}
              channelId={channelId}
              writer={nickname}
              client={client}
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
