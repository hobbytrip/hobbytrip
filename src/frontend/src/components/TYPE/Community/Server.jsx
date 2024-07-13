import { useEffect, useRef, useState } from "react";
import useWebSocketStore from "../../../actions/useWebSocketStore";
import useServerStore from "../../../actions/useServerStore";
import useChatStore from "../../../actions/useChatStore";
import useUserStatusStore from "../../../actions/useUserStatusStore";
import useChannelDatas from "../../../hooks/useChannelDatas";
import s from "./Server.module.css";
import CategoryList from "./CategoryList/CategoryList";
import ChatRoom from "./CommunityChatRoom";
import FriendsList from "./FriendsList/FriendsList";
import MainHeader from "../../MainView/MainHeader/MainHeader";
import MyPlanet from "../../MainView/MyPlanet/MyPlanet";
import API from "../../../utils/API/API";
import TopHeader from "../../Common/ChatRoom/CommunityChatHeader/ChatHeader";
import InviteServer from "../../Modal/ServerModal/Servers/InviteServer/InviteServer";
import { useNavigate, useParams } from "react-router-dom";
import { TiUserAdd } from "react-icons/ti";
import { RiSettings3Fill } from "react-icons/ri";

//최상단. 데탑버전
function Server() {
  const [isInviteOpen, setInviteOpen] = useState(false);
  const { serverData } = useServerStore.getState();
  const nav = useNavigate();
  const subscriptionRef = useRef(null); // 구독 객체 참조 추가
  const { serverId, channelId } = useParams(); //channelId와 serverId 가져오기
  const updateUserState = useUserStatusStore((state) => state.updateUserState);
  const { client } = useWebSocketStore();
  const { setMessage } = useChatStore();
  console.error(serverData);
  console.error(serverData.serverChannels)

  const { getChannelName, getChannelTypeIcon } = useChannelDatas(channelId, serverData.serverChannels );

  let CURRENT_SERVER = serverId;

  useEffect(() => {
    if (serverId === serverData.serverInfo.serverId) {
      CURRENT_SERVER = serverData.serverInfo.serverId;
    }

    if (client) {
      console.log("CURRENT_SERVER", CURRENT_SERVER);
      subscriptionRef.current = client.subscribe(
        API.SUBSCRIBE_CHAT(CURRENT_SERVER), //현재 서버ID를 구독
        (frame) => {
          try {
            const parsedMessage = JSON.parse(frame.body); //받은 메세지 파싱
            //온오프라인 처리
            if (parsedMessage.actionType === "CONNECT") {
              updateUserState(parsedMessage.userId, "ONLINE");
            } else if (parsedMessage.actionType === "DISCONNECT") {
              updateUserState(parsedMessage.userId, "OFFLINE");
            } else if (
              //그외 전송, 수정, 삭제
              parsedMessage.actionType === "SEND" ||
              parsedMessage.actionType === "MODIFY" ||
              parsedMessage.actionType === "DELETE"
            ) {
              //store에 set하여 채팅방으로 넘긴다.
              //useChatStore의 MESSAGE: parsedMessage
              setMessage(parsedMessage);
            }
          } catch (error) {
            console.error("구독 오류: ", error);
          }
        }
      );
      // }
    }

    //클린업 함수, server가 바뀌면 기존 server 구독을 해제한다.
    return () => {
      if (client && subscriptionRef.current) {
        client.unsubscribe(subscriptionRef.current.id);
        subscriptionRef.current = null;
      }
    };
  }, [CURRENT_SERVER]);

  const handleInviteClick = () => {
    setInviteOpen(true);
  };

  const handleInviteClose = () => {
    setInviteOpen(false);
  };

  return (
    <div className={s.wrapper}>
      <div className={s.Servers}>
        <div className={s.deskServers}>
          <MainHeader className={s.mainHeader} />
          <MyPlanet className={s.myPlanet} />
        </div>
      </div>
      <div className={s.container}>
        <div className={s.header}>
          <TopHeader />
        </div>
        <div className={s.deskHeader}>
          <div className={s.serverName}>
            <h3>{serverData.serverInfo.name}</h3>
          </div>
          <div className={s.channelName}>
            <h3>
              {getChannelTypeIcon()}
              {getChannelName()}
            </h3>
          </div>
          <div className={s.modals}>
            <TiUserAdd onClick={handleInviteClick} />
            <RiSettings3Fill
              onClick={() => nav(`/${serverData.serverInfo.serverId}/setting`)}
            />
          </div>
        </div>
        <div className={s.item}>
          <div className={s.CategoryList}>
            <CategoryList />
          </div>
          <div className={s.ChatRoom}>
            <ChatRoom />
          </div>
          <div className={s.FriendsList}>
            <FriendsList />
          </div>
        </div>
      </div>
      {isInviteOpen && <InviteServer onClose={handleInviteClose} />}
    </div>
  );
}

export default Server;
