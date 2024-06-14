import React, { useState, useEffect, useRef } from "react";
import { useParams, useLocation } from "react-router-dom";
// import { useQuery } from "@tanstack/react-query";
import s from "./ChatRoom.module.css";
//components
import TopHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import MessageSender from "../../../components/Modal/ForumModal/MessageSender/MessageSender";
import ChatMessage from "../../../components/Common/ChatRoom/ChatMessage/ChatMessage";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
import ForumThumb from "../../../components/Modal/ForumModal/ForumThumb/ForumThumb";
//stores
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
  //타입 지정
  const TYPE = "forum";
  //useLocation을 사용하여 상태에서 포럼 정보 가져옴.
  const location = useLocation();
  const forum = location.state?.forum;
  const forumCreatedDate = new Date(forum.createAt);
  const formattedDate = `${forumCreatedDate.getFullYear()}년 ${forumCreatedDate.getMonth() + 1}월 ${forumCreatedDate.getDate()}일`;

  const { userId, nickname } = useUserStore();
  const { serverId, channelId, forumId } = useParams();
  const [page, setPage] = useState(0);
  const chatListContainerRef = useRef(null);
  const { accessToken } = useAuthStore();
  const { client, isConnected } = useWebSocketStore();

  //ForumStore
  const {
    addForumMessage,
    modifyForumMessage,
    deleteForumMessage,
    setForumTypingUsers,
    setForumList,
  } = useForumStore((state) => ({
    addForumMessage: state.addForumMessage,
    modifyForumMessage: state.modifyForumMessage,
    deleteForumMessage: state.deleteForumMessage,
    setForumTypingUsers: state.setForumTypingUsers,
    setForumList: state.setForumList,
  }));
  const forumTypingUsers = useForumStore();
  const messages = useForumStore(
    (state) => state.forumLists[serverId]?.[forumId] || []
  ); //해당 서버>포럼에 있는 message 가져오기

  useEffect(() => {
    if (client && isConnected) {
      console.log(client, isConnected);
      connectWebSocket(serverId);
      fetchForumHistory(page, serverId, forumId, setForumList);
      console.log("forum from chat room", forum);
    }
    console.log("server", serverId);
    console.log("channel", channelId);
    console.log("forum", forumId);
  }, [serverId, forumId]); //포럼 id가 바뀔 때마다

  const connectWebSocket = (serverId) => {
    client.subscribe(API.SUBSCRIBE_CHAT(serverId), (frame) => {
      try {
        console.log("subscribe success", serverId);
        const parsedMessage = JSON.parse(frame.body);
        const files = parsedMessage.files
          ? JSON.parse(parsedMessage.files)
          : [];
        //포럼
        if (parsedMessage.chatType === "FORUM") {
          if (
            parsedMessage.actionType === "TYPING" &&
            parsedMessage.userId !== userId
          ) {
            setForumTypingUsers((prevTypingUsers) => {
              if (!prevTypingUsers.includes(parsedMessage.writer)) {
                return [...prevTypingUsers, parsedMessage.writer];
              }
              return prevTypingUsers;
            });
          } else if (parsedMessage.actionType === "STOP_TYPING") {
            setForumTypingUsers((prevTypingUsers) =>
              prevTypingUsers.filter(
                (username) => username !== parsedMessage.writer
              )
            );
          } else if (parsedMessage.actionType === "SEND") {
            // 전송
            addForumMessage(
              parsedMessage.serverId,
              parsedMessage.forumId,
              parsedMessage
            );
            if (files && files.length > 0) {
              const fileUrls = files.map((file) => file.fileUrl);
              const messageWithFiles = {
                parsedMessage,
                files: [...fileUrls],
              };
              addForumMessage(
                messageBody.serverId,
                messageBody.forumId,
                messageWithFiles
              );
              client.publish({
                destination: API.SEND_CHAT(TYPE),
                body: JSON.stringify(messageWithFiles),
              });
            }
          } else if (parsedMessage.actionType === "MODIFY") {
            //수정
            modifyForumMessage(
              parsedMessage.serverId,
              parsedMessage.forumId,
              parsedMessage.messageId,
              parsedMessage.content
            );
          } else if (parsedMessage.actionType === "DELETE") {
            //삭제
            deleteForumMessage(
              parsedMessage.serverId,
              parsedMessage.forumId,
              parsedMessage.messageId
            );
          }
        }
      } catch (error) {
        console.error("구독 오류", error);
      }
    });
  };

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
    const modifiedMessage = messages.find(
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
            style={{ overflowY: "auto", height: "720px", marginTop: "5px" }}
          >
            <div
              className={s.forumTop}
              style={{
                marginTop: "5px",
                marginBottom: "15px",
                paddingBottom: "5px",
                borderBottom: "3px solid #e7e7e7",
              }}
            >
              {forum && (
                <div>
                  <ForumThumb forum={forum} />
                  <h4 className={s.dateHeader}>{formattedDate}</h4>
                  <ChatMessage
                    key={forum.createAt}
                    message={forum}
                    onModifyMessage={handleModifyMessage}
                    onDeleteMessage={handleDeleteMessage}
                  />
                </div>
              )}
            </div>

            <InfiniteScrollComponent
              dataLength={messages.length}
              next={updatePage}
              hasMore={true}
              scrollableTarget="chatListContainer"
            >
              {Object.keys(groupedMessages).map((date) => (
                <div key={date}>
                  {date !== formattedDate && (
                    <h4 className={s.dateHeader}>{date}</h4>
                  )}
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
