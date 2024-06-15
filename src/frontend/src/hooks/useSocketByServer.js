import { useEffect } from "react";
import useUserStore from "../actions/useUserStore";
import useChatStore from "../actions/useChatStore";
import useForumStore from "../actions/useForumStore";
import useWebSocketStore from "../actions/useWebSocketStore";
import useUserStatusStore from "../actions/useUserStatusStore";
import API from "../utils/API/API";

const useWebSocket = (serverId) => {
  const { userId } = useUserStore();
  const { client } = useWebSocketStore();

  const { setTypingUsers, deleteMessage, modifyMessage, sendMessage } =
    useChatStore();

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

  useEffect(() => {
    let subscription;

    const connectWebSocket = () => {
      subscription = client.subscribe(API.SUBSCRIBE_CHAT(serverId), (frame) => {
        try {
          console.log("subscribe success", serverId);
          const parsedMessage = JSON.parse(frame.body);
          switch (parsedMessage.type) {
            case "CONNECT":
              updateUserState(parsedMessage.userId, "ONLINE");
              break;
            case "DISCONNECT":
              updateUserState(parsedMessage.userId, "OFFLINE");
              break;
            default:
              break;
          }
          if (parsedMessage.chatType === "SERVER") {
            handleServerMessage(parsedMessage);
          } else if (parsedMessage.chatType === "FORUM") {
            handleForumMessage(parsedMessage);
          }
        } catch (error) {
          console.error("Subscription error", error);
        }
      });
    };

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
        modifyMessage(serverId, parsedMessage.messageId, parsedMessage.content);
      } else if (parsedMessage.actionType === "DELETE") {
        deleteMessage(serverId, parsedMessage.messageId);
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

    if (serverId) {
      connectWebSocket();
      console.log("구독한 서버: ", serverId);
    }

    //클린업 함수 추가
    return () => {
      if (subscription) {
        subscription.unsubscribe();
        console.log("구독 해제: ", serverId);
      }
    };
  }, [serverId]);
};

export default useWebSocket;
