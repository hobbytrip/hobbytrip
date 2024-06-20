import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import { IoChatbubbleEllipses } from "react-icons/io5";
import s from "../../Common/ChatRoom/ChatRoom.module.css";
import ChatRoomInfo from "../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo";
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar";
import MessageSender from "../../../components/Modal/ChatModal/CreateChatModal/MessageSender/MessageSender";
import ChatMessage from "../../../components/Modal/ChatModal/ChatMessage/ChatMessage";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
import useChatStore from "../../../actions/useChatStore";
import API from "../../../utils/API/API";
import useUserStore from "../../../actions/useUserStore";
import useAuthStore from "../../../actions/useAuthStore";
import axios from "axios";
import { axiosInstance } from "../../../utils/axiosInstance";
import useServerStore from "../../../actions/useServerStore";
import useWebSocketStore from "../../../actions/useWebSocketStore";

const fetchChatHistory = async (page, channelId, setMessages) => {
  const token = localStorage.getItem("accessToken");
  try {
    const response = await axios.get(API.GET_HISTORY, {
      params: {
        channelId: channelId,
        page: page,
        size: 20,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
      withCredentials: true,
    });
    const responseData = response.data.data;
    if (responseData) {
      const historys = responseData.data.reverse();
      console.log(historys);
      setMessages(historys);
    }
  } catch (err) {
    console.error("채팅 기록 불러오기 오류", err);
    throw new Error("채팅 기록 불러오기 오류");
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
  const { USER } = useUserStore();
  const { accessToken } = useAuthStore();
  const { serverData } = useServerStore();
  const { client } = useWebSocketStore();
  const { removeMessage } = useChatStore();

  const { userInfos } = serverData.serverUserInfos;
  const userId = USER.userId;
  const nickname = USER.name;

  const { serverId, channelId } = useParams();
  let CURRENT_SERVER = serverId;
  let CURRENT_CHANNEL = channelId;
  //서버에 있는 유저 저장 MAP
  const SERVER_USER_MAP = userInfos?.map((user) => user.userId);
  const TYPE = "server";
  //store에 들어온 메세지 let 변수에 담아줌.
  let chatMessage = useChatStore((state) => state.MESSAGE);
  //해당 채팅방의 메세지를 관리
  const [messages, setMessages] = useState([]);
  //무한스크롤을 위한 Ref
  const chatListContainerRef = useRef(null);
  const [page, setPage] = useState(0);

  useEffect(() => {
    //채팅기록 fetch -> channelID 바뀔 때
    console.log("CURRENT_SERVER", CURRENT_SERVER);
    console.log("CURRENT_CHANNEL", CURRENT_CHANNEL);
    fetchChatHistory(page, CURRENT_CHANNEL, setMessages);
  }, [CURRENT_CHANNEL]);

  //사용자 위치 서버에 POST -> 서버와 채널 바뀔 때
  useEffect(() => {
    postUserLocation(userId, CURRENT_SERVER, CURRENT_CHANNEL);
  }, [CURRENT_SERVER, CURRENT_CHANNEL]);

  useEffect(() => {
    if (chatListContainerRef.current) {
      chatListContainerRef.current.scrollTop =
        chatListContainerRef.current.scrollHeight;
    }
  }, [messages]);

  useEffect(() => {
    if (chatMessage) {
      console.log("chatMessage O");
      //현재 채널 메세지만 렌더링
      if (chatMessage.channelId === CURRENT_CHANNEL) {
        if (chatMessage.actionType === "MODIFY") {
          setMessages((prevMessages) => {
            return [...prevMessages].map((prevMessage) => {
              if (prevMessage.messageId === chatMessage.messageId) {
                return { ...prevMessage, message: chatMessage.message };
              } else {
                return prevMessage;
              }
            });
          });
        } else if (chatMessage.actionType === "DELETE") {
          setMessages((prevMessages) => {
            return prevMessages.filter(
              (prevMessage) => prevMessage.messageId !== chatMessage.messageId
            );
          });
        } else {
          setMessages((prevMessages) => [...prevMessages, chatMessage]);
        }
      } else {
        console.log("no chatMessage");
      }
    }

    //처리가 끝난 메세지는 store에서 제거
    removeMessage(chatMessage);
  }, [chatMessage]);

  //서버에 publish
  const handleSendMessage = async (messageContent, uploadedFiles) => {
    const messageBody = {
      serverId: CURRENT_SERVER,
      channelId: CURRENT_CHANNEL,
      userId: userId,
      parentId: 0,
      profileImage: "ho",
      writer: nickname,
      content: messageContent,
      createdAt: new Date().toISOString(),
      receiverIds: SERVER_USER_MAP,
      mentionType: "EVERYONE",
    };

    if (uploadedFiles.length >= 1) {
      await uploadFiles(messageBody, uploadedFiles);
    }

    client.publish({
      destination: API.SEND_CHAT(TYPE),
      body: JSON.stringify(messageBody),
    });
  };

  const uploadFiles = async (messageBody, uploadedFiles) => {
    const formData = new FormData();
    const jsonMsg = JSON.stringify(messageBody);
    const req = new Blob([jsonMsg], { type: "application/json" });
    formData.append("createRequest", req);
    uploadedFiles.forEach((file) => {
      formData.append("files", file);
    });
    try {
      await axios.post(API.FILE_UPLOAD, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${accessToken}`,
        },
        withCredentials: true,
      });
    } catch (error) {
      console.error("파일 업로드 오류:", error);
      throw new Error("파일 업로드 오류");
    }
  };

  const handleModifyMessage = (messageId, newContent) => {
    const messageBody = {
      serverId: serverId,
      messageId: messageId,
      content: newContent,
      actionType: "MODIFY",
    };

    client.publish({
      destination: API.MODIFY_CHAT(TYPE),
      body: JSON.stringify(messageBody),
    });
  };

  const handleDeleteMessage = (messageId) => {
    const messageBody = {
      serverId: serverId,
      messageId: messageId,
      actionType: "DELETE",
    };
    client.publish({
      destination: API.DELETE_CHAT(TYPE),
      body: JSON.stringify(messageBody),
    });
  };

  const updatePage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  const groupedMessages = groupMessagesByDate(messages);

  return (
    <div className={s.chatRoomWrapper}>
      <div className={s.wrapper}>
        <div className={s.topContainer}>
          <ChatRoomInfo />
          <ChatSearchBar />
        </div>
        <div className={s.chatContainer}>
          <div
            ref={chatListContainerRef}
            id="chatListContainer"
            className={s.chatListContainer}
            style={{ overflowY: "auto" }}
          >
            <div className={s.topInfos}>
              <IoChatbubbleEllipses className={s.chatIcon} />
              <h1>채팅을 시작해보세요!</h1>
            </div>

            <InfiniteScrollComponent
              dataLength={Object.keys(messages).length}
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
            {/* {typingUsers.length > 0 && (
              <div className="typingIndicator">
                {typingUsers.length >= 5
                  ? "여러 사용자가 입력 중입니다..."
                  : `${typingUsers.join(", ")} 입력 중입니다...`}
              </div>
            )} */}
            <MessageSender
              onMessageSend={handleSendMessage}
              serverId={CURRENT_SERVER}
              channelId={CURRENT_CHANNEL}
              writer={nickname}
              client={client}
              TYPE={TYPE}
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
