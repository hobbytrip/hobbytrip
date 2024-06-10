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
import useUserStore from "../../../actions/useUserStore";
import useAuthStore from "../../../actions/useAuthStore";
import API from "../../../utils/API/API";
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
    const datas = responseData.data.reverse();
    console.log("fetch forum chats", datas);
    // console.log("data", datas);
    if (Array.isArray(datas) && datas) {
      setForumList(serverId, forumId, datas);
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
  const TYPE = "forum";

  //ForumStore
  const {
    addForumMessage,
    modifyForumMessage,
    deleteForumMessage,
    typingForumUsers,
    setForumList,
  } = useForumStore((state) => ({
    addForumMessage: state.addForumMessage,
    modifyForumMessage: state.modifyForumMessage,
    deleteForumMessage: state.deleteForumMessage,
    typingForumUsers: state.typingForumUsers,
    setForumList: state.setForumList,
  }));
  const messages = useForumStore(
    (state) => state.forumLists[serverId]?.[forumId] || []
  ); //해당 서버>포럼에 있는 message 가져오기

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
      addForumMessage(messageBody.serverId, messageBody.forumId, messageBody);
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

  //파일 업로드
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

  //메세지 수정
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
      modifyForumMessage(serverId, forumId, messageId, newContent);
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

    deleteForumMessage(serverId, forumId, messageId);
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
  }, [messages]);

  const groupedMessages = groupMessagesByDate(messages);

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
              dataLength={messages.length}
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
            {typingForumUsers.length > 0 && (
              <div className="typingIndicator">
                {typingForumUsers.length >= 5
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

  console.log("messages:", messages);

  if (Array.isArray(messages) && messages) {
    messages.forEach((message) => {
      const date = new Date(message.createdAt);
      const dateString = `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`;

      if (!groupedMessages[dateString]) {
        groupedMessages[dateString] = [];
      }
      groupedMessages[dateString].push(message);
    });
  } else {
    console.error("not array");
  }

  return groupedMessages;
};
export default ForumChat;
