import { useQuery } from "@tanstack/react-query";
import { useState, useEffect, useRef } from "react";
import axios from "axios";
import { axiosInstance } from "../utils/axiosInstance";
import useWebSocketStore from "../actions/useWebSocketStore";
import useChatStore from "../actions/useChatStore";
import API from "../utils/API/API";

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
      userId,
      serverId,
      channelId,
    });
  } catch (err) {
    console.error("사용자 위치 POST 실패", err);
    throw new Error("사용자 위치 POST 실패");
  }
};

const useChat = (serverId, channelId, userId, TYPE) => {
  const [page, setPage] = useState(0);
  const { client, isConnected } = useWebSocketStore();
  const {
    chatList,
    sendMessage,
    modifyMessage,
    deleteMessage,
    setTypingUsers,
  } = useChatStore.getState();

  const { data, error, isLoading } = useQuery({
    queryKey: ["messages", channelId, page],
    queryFn: fetchChatHistory,
    staleTime: 1000 * 60 * 5,
    keepPreviousData: true,
  });

  useEffect(() => {
    postUserLocation(userId, serverId, channelId);
    if (client && isConnected) {
      client.unsubscribe(serverId);
    }
    if (client) {
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
            if (client && client.publish) {
              client.publish({
                destination: API.SEND_CHAT,
                body: JSON.stringify(parsedMessage),
              });
            }
          } else if (parsedMessage.actionType === "MODIFY") {
            modifyMessage(parsedMessage.messageId, parsedMessage.content);
            if (client && client.publish) {
              client.publish({
                destination: API.MODIFY_CHAT,
                body: JSON.stringify(parsedMessage),
              });
            }
          } else if (parsedMessage.actionType === "DELETE") {
            deleteMessage(parsedMessage.messageId);
            if (client && client.publish) {
              client.publish({
                destination: API.DELETE_CHAT,
                body: JSON.stringify(parsedMessage),
              });
            }
          }
        } catch (error) {
          console.error("구독 오류", error);
        }
      });
    }
  }, [serverId]);

  useEffect(() => {
    if (data && Array.isArray(data)) {
      if (Array.isArray(data).length === 0) {
        console.error("빈 채팅목록");
      } else {
        useChatStore.setState({ chatList: data });
      }
    }
  }, [data]);

  const handleSendMessage = async (messageContent, uploadedFiles) => {
    const messageBody = {
      serverId,
      channelId,
      userId,
      parentId: 0,
      profileImage: "ho",
      writer: userId,
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
        uploadedFiles.forEach((file) => formData.append("files", file));
        await axios.post(API.FILE_UPLOAD, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
          withCredentials: true,
        });
      }
      sendMessage(messageBody);
      client.publish({
        destination: API.SEND_CHAT(TYPE),
        body: JSON.stringify(messageBody),
      });
    } catch (error) {
      console.error("메시지 전송 오류:", error);
    }
  };

  return {
    chatList,
    isLoading,
    error,
    handleSendMessage,
    handleModifyMessage: (messageId, newContent) => {
      const messageBody = {
        serverId,
        messageId,
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
          destination: API.MODIFY_CHAT(TYPE),
          body: JSON.stringify(messageBody),
        });
      }
    },
    handleDeleteMessage: (messageId) => {
      const messageBody = { serverId, messageId, actionType: "DELETE" };
      deleteMessage(messageId);
      client.publish({
        destination: API.DELETE_CHAT(TYPE),
        body: JSON.stringify(messageBody),
      });
    },
  };
};

export default useChat;
