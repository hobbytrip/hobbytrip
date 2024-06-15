import React, { useState, useEffect, useRef } from "react";
import { useParams, useLocation } from "react-router-dom";
import s from "./ChatRoom.module.css";
//components
import TopHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import MessageSender from "../../../components/Modal/ForumModal/MessageSender/MessageSender";
import ChatMessage from "../../../components/Common/ChatRoom/ChatMessage/ChatMessage";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
//stores
import useWebSocketStore from "../../../actions/useWebSocketStore";
import useDmStore from "../../../actions/useDmStore";
import useUserStore from "../../../actions/useUserStore";
import useAuthStore from "../../../actions/useAuthStore";
import API from "../../../utils/API/API";
import axios from "axios";

//dm 기록 받아오기
const fetchDmHistory = async (page, roomId, setForumList) => {
  const token = localStorage.getItem("accessToken");
  try {
    const response = await axios.get(API.GET_DM_HISTORY, {
      params: {
        roomId,
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
    console.log("fetch dm chats", datas);
    // console.log("data", datas);
    if (Array.isArray(datas) && datas) {
      setForumList(serverId, forumId, datas);
    }
  } catch (err) {
    console.error("포럼 기록 불러오기 오류", err);
    throw new Error("포럼 기록 불러오기 오류");
  }
};

function DmChat() {
  //타입 지정: dm
  const TYPE = "direct";
  const { userId, nickname } = useUserStore();
  const { roomId } = useParams();
  const [page, setPage] = useState(0);
  const chatListContainerRef = useRef(null);
  const { accessToken } = useAuthStore();
  const { client, isConnected } = useWebSocketStore();

  //dmStore
  const {
    addDmMessage,
    modifyDmMesage,
    deleteDmMessage,
    setDmTypingUsers,
    setDmList,
  } = useDmStore((state) => ({
    addDmMessage: state.addDmMessage,
    modifyDmMesage: state.modifiedMessage,
    deleteDmMessage: state.deleteDmMessage,
    setDmTypingUsers: state.typingDmUsers,
    setDmList: state.setDmList,
  }));
  const typingDmUsers = useDmStore();
  const dms = useDmStore((state) => state.dmLists[roomId] || []);

  useEffect(() => {
    if (client && isConnected) {
      console.log(client, isConnected);
      connectWebSocket(roomId); //roomId로 채팅방 구독
      fetchDmHistory(page, roomId, setDmList); //기록 받아오기
    }
    console.log("채팅Room ID", roomId);
  }, [roomId]); //room id가 바뀔 때마다

  const connectWebSocket = (roomId) => {
    client.subscribe(API.SUBSCRIBE_DM(roomId), (frame) => {
      try {
        console.log("dm방 구독 성공", roomId);
        // const dmRoomId = roomId;
        const parsedMessage = JSON.parse(frame.body);
        const files = parsedMessage.files
          ? JSON.parse(parsedMessage.files)
          : [];
        //DM
        if (parsedMessage.chatType === "DM") {
          if (
            parsedMessage.actionType === "TYPING" &&
            parsedMessage.userId !== userId
          ) {
            setDmTypingUsers((prevTypingUsers) => {
              if (!prevTypingUsers.includes(parsedMessage.writer)) {
                return [...prevTypingUsers, parsedMessage.writer];
              }
              return prevTypingUsers;
            });
          } else if (parsedMessage.actionType === "SEND") {
            // 전송
            addDmMessage(parsedMessage.dmRoomId, parsedMessage);
            if (files && files.length > 0) {
              const fileUrls = files.map((file) => file.fileUrl);
              const messageWithFiles = {
                parsedMessage,
                files: [...fileUrls],
              };
              addDmMessage(messageBody.dmRoomId, messageWithFiles);
              client.publish({
                destination: API.SEND_CHAT(TYPE),
                body: JSON.stringify(messageWithFiles),
              });
            }
          } else if (parsedMessage.actionType === "MODIFY") {
            //수정
            modifyDmMessage(
              parsedMessage.dmRoomId,
              parsedMessage.messageId,
              parsedMessage.content
            );
          } else if (parsedMessage.actionType === "DELETE") {
            //삭제
            deleteDmMessage(parsedMessage.dmRoomId, parsedMessage.messageId);
          }
        }
      } catch (error) {
        console.error("DM 구독 오류", error);
      }
    });
  };

  const handleSendMessage = async (messageContent, uploadedFiles) => {
    const messageBody = {
      dmRoomId: roomId,
      userId: userId,
      parentId: 0,
      profileImage: "ho",
      writer: nickname,
      content: messageContent,
      receiverIds: [],
      createdAt: new Date().toISOString(),
    };
    const sendMessageWithoutFile = (messageBody) => {
      addDmMessage(messageBody.dmRoomId, messageBody);
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
      await axios.post(API.DM_FILE_UPLOAD, formData, {
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
      dmRoomId: roomId,
      messageId: messageId,
      content: newContent,
      actionType: "MODIFY",
    };
    const modifiedMessage = dms.find(
      (message) => message.messageId === messageId
    );
    if (modifiedMessage) {
      modifiedMessage.content = newContent;
      modifyDmMesage(roomId, messageId, newContent);
      client.publish({
        destination: API.MODIFY_CHAT(TYPE),
        body: JSON.stringify(messageBody),
      });
    }
  };

  const handleDeleteMessage = (messageId) => {
    const messageBody = {
      dmRoomId: roomId,
      messageId: messageId,
    };

    deleteDmMessage(roomId, messageId);
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
  }, [dms]);

  const groupedMessages = groupMessagesByDate(dms);

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
            <InfiniteScrollComponent
              dataLength={dms.length}
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
            {typingDmUsers.length > 0 && (
              <div className="typingIndicator">
                {typingDmUsers.length >= 5
                  ? "여러 사용자가 입력 중입니다..."
                  : `${typingDmUsers.join(", ")} 입력 중입니다...`}
              </div>
            )}
            <MessageSender
              onMessageSend={handleSendMessage}
              roomId={roomId}
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
export default DmChat;
