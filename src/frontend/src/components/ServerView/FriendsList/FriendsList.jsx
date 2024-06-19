import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import style from "./FriendsList.module.css";
import useUserStore from "../../../actions/useUserStore";
import useServerStore from "../../../actions/useServerStore";
import { IoClose } from "react-icons/io5";
import { FaCrown } from "react-icons/fa"; // 관리자 왕관 표시
import useUserStatusStore from "../../../actions/useUserStatusStore";

const FriendsList = () => {
  const { serverData } = useServerStore((state) => ({
    serverData: state.serverData,
  }));

  const managerId = serverData.serverInfo.managerId;
  const serverUserStatus = serverData.userStatus.usersConnectionState; //유저 상태
  const serverUserInfos = serverData.serverUserInfos; //서버 유저 정보
  const { userId } = useUserStore();
  const nav = useNavigate();

  const setUserStatus = useUserStatusStore((state) => state.setUsers);
  const { users, onlineUsers, offlineUsers } = useUserStatusStore();

  const handleBack = () => {
    nav(-1);
  };

  useEffect(() => {
    setUserStatus(serverUserStatus);
    console.log("온라인:", onlineUsers);
    console.log("오프라인:", offlineUsers);
    console.log(serverUserInfos);
    console.log("managerId", managerId);
  }, [serverUserStatus, setUserStatus]);

  return (
    <>
      <div className={style.wrapper}>
        <div className={style.listHeader}>
          <button onClick={handleBack} className={style.backBtn}>
            <IoClose style={{ width: "20px", height: "20px" }} />
          </button>
        </div>
        <div className={style.online}>
          <div className={style.header}>
            <h3> 온라인 - {onlineUsers.length} 명 </h3>
          </div>
          <div className={style.list}>
            <ul>
              {onlineUsers.map((userId) => {
                const userInfo = serverUserInfos.find(
                  (user) => user.userId === Number(userId)
                );
                if (!userInfo) return null;
                return (
                  <li key={userId} className={style.friend}>
                    <div
                      className={style.stateCircle}
                      style={{ backgroundColor: "#70CC40" }}
                    />
                    <img
                      src={`../../../../public/image/no-friendCon.jpg`}
                      className={style.profileImage}
                      alt="프로필 이미지"
                    />
                    <div className={style.name}>{userInfo.name}</div>
                    {userInfo.userId === managerId && (
                      <FaCrown className={style.crown} />
                    )}
                  </li>
                );
              })}
            </ul>
          </div>
        </div>
        <div className={style.offline}>
          <div className={style.header}>
            <h3> 오프라인 - {offlineUsers.length} 명 </h3>
          </div>
          <div className={style.list}>
            <ul>
              {offlineUsers.map((userId) => {
                const userInfo = serverUserInfos.find(
                  (user) => user.userId === Number(userId)
                );
                if (!userInfo) return null;
                return (
                  <li key={userId} className={style.friend}>
                    <div
                      className={style.stateCircle}
                      style={{ backgroundColor: "#CCCCCC" }}
                    />
                    <img
                      src={`../../../../public/image/no-friendCon.jpg`}
                      className={style.profileImage}
                      alt="프로필 이미지"
                    />
                    <div className={style.name}>{userInfo.name}</div>
                    {userInfo.userId === managerId && (
                      <FaCrown className={style.crown} />
                    )}
                  </li>
                );
              })}
            </ul>
          </div>
        </div>
      </div>
    </>
  );
};

export default FriendsList;
