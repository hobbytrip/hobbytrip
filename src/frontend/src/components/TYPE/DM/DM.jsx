// DM.jsx

import React, { useState, useEffect } from "react";
import DmChat from "./DmChatRoom";
import s from "./DM.module.css";
import TopHeader from "../../Common/ChatRoom/CommunityChatHeader/ChatHeader";
import API from "../../../utils/API/API";
import { axiosInstance } from "../../../utils/axiosInstance";
import { useParams } from "react-router-dom";
import useDmHistoryStore from "../../../actions/useDmHistoryStore";
import DmHistoryList from "./DmHistoryList/DmHistoryList";

function DM() {
  const [dmInfo, setDmInfo] = useState(null);
  const { dmId } = useParams();
  const { dmHistoryList, setDmHistoryList } = useDmHistoryStore();

  useEffect(() => {
    const fetchDMInfo = async () => {
      try {
        const response = await axiosInstance.get(API.READ_DM(dmId));
        setDmInfo(response.data.data);
        setDmHistoryList(response.data.data.dmUserInfos);
      } catch (error) {
        console.error("Failed to fetch DM info:", error);
      }
    };

    fetchDMInfo();
  }, [dmId, setDmHistoryList]);

  if (dmInfo === null) {
    return <div>Loading...</div>;
  }

  return (
    <div className={s.wrapper}>
      <div className={s.Servers}>
        <h1>하이</h1>
      </div>
      <div className={s.container}>
        <div className={s.header}>
          <TopHeader />
        </div>
        <div className={s.deskHeader}></div>
        <div className={s.item}>
          <div className={s.dmHistoryList}>
            <DmHistoryList dmId={dmId} dmHistoryList={dmHistoryList} />
          </div>
          <div className={s.ChatRoom}>
            <DmChat
              roomId={dmId}
              usersId={dmHistoryList.map((userInfo) => userInfo.userId)}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default DM;
