import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import style from './FriendsList.module.css';
import useUserStore from '../../../actions/useUserStore';
import useServerStore from '../../../actions/useServerStore';
import { IoClose } from "react-icons/io5";
import { FaCrown } from "react-icons/fa"; // 관리자 왕관 표시

const FriendsList = () => {
  const { serverData } = useServerStore((state) => ({
    serverData: state.serverData,
  }));
  const serverId = serverData.serverInfo.serverId;
  const managerId = serverData.serverInfo.managerId;
  const { userId } = useUserStore();
  const nav = useNavigate();

  const handleBack = () => {
    nav(-1);
  };

  return (
    <>
      <div className={style.wrapper}>
        <div className={style.listHeader}>
          <button onClick={handleBack} className={style.backBtn}>
            <IoClose style={{ width: "18px", height: "18px" }} />
          </button>
        </div>
        <div className={style.online}>
          <div className={style.header}>
            <h3> 온라인 - 인원 </h3>
          </div>
          <div className={style.list}>
            <ul>
              <li className={style.friend}>
                <div className={style.stateCircle} 
                  style={{backgroundColor: "#70CC40"}}/>
                <img src="../../../../public/image/no-friendCon.jpg"
                 className={style.profileImage}/>
                <div className={style.name}>친구 이름</div>
                {/* 관리자라면 */}
                {managerId === userId && <FaCrown className={style.crown}/>}
              </li>
            </ul>
          </div>
        </div>
        <div className={style.offline}>
          <div className={style.header}>
            <h3> 오프라인 - 인원 </h3>
          </div>
          <div className={style.list}>
            <ul>
              <li> 친구 </li>
            </ul>
          </div>
        </div>
      </div>
    </>
  );
};

export default FriendsList;
