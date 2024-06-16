import { useEffect, useRef } from "react";
import useWebSocketStore from "../../../actions/useWebSocketStore";
import useServerStore from "../../../actions/useServerStore";
import useChatStore from "../../../actions/useChatStore";
import useForumStore from "../../../actions/useForumStore";
import useUserStatusStore from "../../../actions/useUserStatusStore";
import s from "./Server.module.css";
import CategoryList from "./CategoryList/CategoryList";
import ChatRoom from "./CommunityChatRoom";
import FriendsList from "./FriendsList/FriendsList";
import API from "../../../utils/API/API";

//최상단. 데탑버전
function Server() {
  const { serverData } = useServerStore((state) => ({
    serverData: state.serverData,
  }));
  const subscriptionRef = useRef(null); // 구독 객체 참조 추가

  const { setTypingUsers, deleteMessage, modifyMessage, sendMessage } =
    useChatStore((state) => ({
      setTypingUsers: state.setTypingUsers,
      deleteMessage: state.deleteMessage,
      modifyMessage: state.modifyMessage,
      sendMessage: state.sendMessage,
    }));

  const {
    addForumMessage,
    modifyForumMessage,
    deleteForumMessage,
    setForumTypingUsers,
  } = useForumStore((state) => ({
    addForumMessage: state.addForumMessage,
    modifyForumMessage: state.modifyForumMessage,
    deleteForumMessage: state.deleteForumMessage,
    setForumTypingUsers: state.setForumTypingUsers,
  }));

  const updateUserState = useUserStatusStore((state) => state.updateUserState);

  const { client } = useWebSocketStore();
  const CURRENT_SERVER = serverData.serverInfo.serverId;

  useEffect(() => {
    if (CURRENT_SERVER) {
      const handleServerMessage = (parsedMessage) => {
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
        } else if (parsedMessage.actionType === "SEND") {
          sendMessage(parsedMessage);
        } else if (parsedMessage.actionType === "MODIFY") {
          modifyMessage(
            parsedMessage.serverId,
            parsedMessage.messageId,
            parsedMessage.content
          );
        } else if (parsedMessage.actionType === "DELETE") {
          deleteMessage(parsedMessage.serverId, parsedMessage.messageId);
        }
      };

      const handleForumMessage = (parsedMessage) => {
        if (parsedMessage.actionType === "SEND") {
          addForumMessage(
            parsedMessage.serverId,
            parsedMessage.forumId,
            parsedMessage
          );
        } else if (parsedMessage.actionType === "MODIFY") {
          modifyForumMessage(
            parsedMessage.serverId,
            parsedMessage.forumId,
            parsedMessage.messageId,
            parsedMessage.content
          );
        } else if (parsedMessage.actionType === "DELETE") {
          deleteForumMessage(
            parsedMessage.serverId,
            parsedMessage.forumId,
            parsedMessage.messageId
          );
        } else if (
          parsedMessage.actionType === "TYPING" &&
          parsedMessage.userId !== userId
        ) {
          setForumTypingUsers((prevTypingUsers) => {
            if (!prevTypingUsers.includes(parsedMessage.writer)) {
              return [...prevTypingUsers, parsedMessage.writer];
            }
            return prevTypingUsers;
          });
        }
      };

      if (client) {
        subscriptionRef.current = client.subscribe(
          API.SUBSCRIBE_CHAT(CURRENT_SERVER),
          (frame) => {
            try {
              console.log("구독 성공: ", CURRENT_SERVER);
              const parsedMessage = JSON.parse(frame.body);
              if (parsedMessage.type === "CONNECT") {
                updateUserState(parsedMessage.userId, "ONLINE");
              } else if (parsedMessage.type === "DISCONNECT")
                updateUserState(parsedMessage.userId, "OFFLINE");

              if (parsedMessage.chatType === "SERVER") {
                handleServerMessage(parsedMessage);
              } else if (parsedMessage.chatType === "FORUM") {
                handleForumMessage(parsedMessage);
              }
            } catch (error) {
              console.error("구독 오류: ", error);
            }
          }
        );
      }
    }
    return () => {
      if (client) {
        client.unsubscribe(subscriptionRef.current.id);
        subscriptionRef.current = "";
      }
    };
  }, [CURRENT_SERVER]);

  return (
    <div className={s.wrapper}>
      <CategoryList />
      <ChatRoom />
      <FriendsList />
    </div>
  );
}

export default Server;
