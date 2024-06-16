import React from "react";
import s from "./DmHistoryList.module.css";
import { RiGroupFill } from "react-icons/ri";
import { useNavigate } from "react-router-dom";

const DmHistoryList = ({ dmId, dmHistoryList }) => {
  const navigate = useNavigate();
  const formatUserNames = (users) => {
    return users.map((user) => user.name).join(", ");
  };

  const handleMoveToDm = () => {
    navigate(`/${dmId}/dm`);
  };

  return (
    <div className={s.wrapper}>
      <h3 className={s.title}>다이렉트 메세지</h3>
      <div className={s.dmHistoryList}>
        <div className={s.dmRoomIcon}>
          <RiGroupFill className={s.groupIcon} />
        </div>
        <div className={s.container}>
          <div className={s.dmBox} onClick={handleMoveToDm}>
            <h3 style={{ fontWeight: "600" }}>
              {formatUserNames(dmHistoryList)}
            </h3>
          </div>
          <h4 className={s.count}>총 {dmHistoryList.length}명</h4>
        </div>
      </div>
    </div>
  );
};

export default DmHistoryList;
