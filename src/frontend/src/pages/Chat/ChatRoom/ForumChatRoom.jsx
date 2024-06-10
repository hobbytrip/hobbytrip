import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
// import { useQuery } from "@tanstack/react-query";
import { IoChatbubbleEllipses } from "react-icons/io5";
import s from "./ChatRoom.module.css";
import TopHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import MessageSender from "../../../components/Modal/ForumModal/MessageSender/MessageSender";
import ChatMessage from "../../../components/Modal/ChatModal/ChatMessage/ChatMessage";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
import useWebSocketStore from "../../../actions/useWebSocketStore";
import useForumStore from "../../../actions/useForumStore";
import API from "../../../utils/API/API";
import useUserStore from "../../../actions/useUserStore";
import useAuthStore from "../../../actions/useAuthStore";
import axios from "axios";

const fetchForumHistory = async (page, serverId, forumId, setForumList) => {
  const token = localStorage.getItem("accessToken");
  try {
    const response = await axios.get(API.GET_FORUM_HISTORY, {
      params: {
        forumId: forumId,
        page: page,
        size: 20,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
      withCredentials: true,
    });
    const responseData = response.data.data;
    console.log("responseData", responseData);
    if (responseData) {
      setForumList(serverId, forumId, responseData.data.reverse());
    }
  } catch (err) {
    console.error("포럼 기록 불러오기 오류", err);
    throw new Error("포럼 기록 불러오기 오류");
  }
};

function ForumChat() {
  const { userId, nickname } = useUserStore();
  const { serverId, channelId, forumId } = useParams();
  const [page, setPage] = useState(0);
  const chatListContainerRef = useRef(null);
  const { accessToken } = useAuthStore();
  const { client, isConnected } = useWebSocketStore();

  const {
    setForumList,
    forumTypingUsers,
    deleteForumChat,
    modifyForumChat,
    sendForumChat,
    getForumList,
  } = useForumStore();
  const TYPE = "forum";
  const forumList = getForumList(serverId, forumId);

  useEffect(() => {
    if (client && isConnected) {
      fetchForumHistory(page, serverId, forumId, setForumList);
    }
    console.log("server", serverId);
    console.log("channel", channelId);
    console.log("forum", forumId);
  }, [forumId]);

  const handleSendMessage = async (messageContent, uploadedFiles) => {
    const messageBody = {
      serverId: serverId,
      forumId: forumId,
      channelId: channelId,
      userId: userId,
      parentId: 0,
      profileImage: "ho",
      writer: nickname,
      content: messageContent,
      createdAt: new Date().toISOString(),
    };

    const sendMessageWithoutFile = (messageBody) => {
      sendForumChat(messageBody);
      client.publish({
        destination: API.SEND_CHAT(TYPE),
        body: JSON.stringify(messageBody),
      });
    };

    if (uploadedFiles.length >= 1) {
      await uploadFiles(messageBody, uploadedFiles);
    } else {
      sendMessageWithoutFile(messageBody);
    }
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
      await axios.post(API.FORUM_FILE_UPLOAD, formData, {
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
    const modifiedMessage = forumList.find(
      (message) => message.messageId === messageId
    );
    if (modifiedMessage) {
      modifiedMessage.content = newContent;
      modifyForumChat(serverId, forumId, messageId, newContent);
      client.publish({
        destination: API.MODIFY_CHAT(TYPE),
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

    deleteForumChat(serverId, forumId, messageId);
    client.publish({
      destination: API.DELETE_CHAT(TYPE),
      body: JSON.stringify(messageBody),
    });
  };

  const updatePage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  useEffect(() => {
    if (chatListContainerRef.current) {
      chatListContainerRef.current.scrollTop =
        chatListContainerRef.current.scrollHeight;
    }
  }, [forumList]);

  const groupedMessages = groupMessagesByDate(forumList);

  return (
    <div className={s.chatRoomWrapper}>
      <div className={s.wrapper}>
        <div className={s.topContainer}>
          <TopHeader />
        </div>
        <div className={s.chatContainer}>
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
              dataLength={forumList.length}
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
            {forumTypingUsers.length > 0 && (
              <div className="typingIndicator">
                {forumTypingUsers.length >= 5
                  ? "여러 사용자가 입력 중입니다..."
                  : `${forumTypingUsers.join(", ")} 입력 중입니다...`}
              </div>
            )}
            <MessageSender
              onMessageSend={handleSendMessage}
              serverId={serverId}
              channelId={channelId}
              forumId={forumId}
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
export default ForumChat;
