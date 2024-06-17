import React, { useState, useEffect } from "react";
import DmChat from "./DmChatRoom";
import s from "./DM.module.css";
import TopHeader from "../../Common/ChatRoom/CommunityChatHeader/ChatHeader";
import API from "../../../utils/API/API";
import { axiosInstance } from "../../../utils/axiosInstance";
import { useParams } from "react-router-dom";
import { RiGroupFill } from "react-icons/ri";
import useDmHistoryStore from "../../../actions/useDmHistoryStore";
import DmHistoryList from "./DmHistoryList/DmHistoryList";
import UserList from "./UserList/UserList";
import MainHeader from "../../MainView/MainHeader/MainHeader";
import MyPlanet from "../../MainView/MyPlanet/MyPlanet";

function DM() {
  const [dmInfo, setDmInfo] = useState(null);
  const { dmId } = useParams();
  const { dmHistoryList, setDmHistoryList } = useDmHistoryStore();

  useEffect(() => {
    const fetchDMInfo = async () => {
      try {
        const response = await axiosInstance.get(API.READ_DM(dmId));
        setDmInfo(response.data.data);
        console.log(dmInfo);
        // setDmHistoryList(response.data.data.dm);
        useDmHistoryStore.getState().addDmHistory(response.data.data.dm);
      } catch (error) {
        console.error("Failed to fetch DM info:", error);
      }
    };

    fetchDMInfo();
  }, [dmId]);

  if (dmInfo === null) {
    return <div>Loading...</div>;
  }

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
          <div className={s.dmRoomIcon}>
            <RiGroupFill className={s.groupIcon} />
          </div>
          <h3 className={s.headerContent}>{dmInfo.dm.name}</h3>
        </div>
        <div className={s.item}>
          <div className={s.dmHistoryList}>
            <DmHistoryList dmHistoryList={dmHistoryList} />
          </div>
          <div className={s.ChatRoom}>
            <DmChat
              roomId={dmId}
              usersId={dmHistoryList.map((userInfo) => userInfo.userId)}
            />
          </div>
          <div className={s.userList}>
            <UserList dm={dmInfo} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default DM;
