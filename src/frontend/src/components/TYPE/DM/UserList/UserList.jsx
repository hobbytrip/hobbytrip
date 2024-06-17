import React from "react";
import s from "./UserList.module.css";

const UserList = ({ dm }) => {
  if (!dm || !dm.userConnectionState || !dm.dmUserInfos) {
    console.log(dm);
    return <div className={s.userList}>Loading...</div>;
  }

  const getUserById = (userId) => {
    const user = dm.dmUserInfos.find(
      (user) => user.userId === parseInt(userId)
    );
    return user ? user.name : "Unknown User";
  };

  return (
    <div className={s.userList}>
      <div className={s.header}>
        <h3>멤버 - {dm.dmUserInfos.length}명</h3>
      </div>
      <ul className={s.list}>
        {Object.keys(dm.userConnectionState.usersConnectionState).map(
          (userId) => (
            <li key={userId} className={s.user}>
              <div
                className={s.stateCircle}
                style={{
                  backgroundColor:
                    dm.userConnectionState.usersConnectionState[userId] ===
                    "ONLINE"
                      ? "#70CC40" // 초록색 배경
                      : "#CCCCCC", // 회색 배경
                }}
              />
              <h3 className={s.userName}>{getUserById(userId)}</h3>
            </li>
          )
        )}
      </ul>
    </div>
  );
};

export default UserList;
