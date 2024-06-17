import React from "react";
import s from "./DmHistoryList.module.css";
import { RiGroupFill } from "react-icons/ri";
import { useNavigate } from "react-router-dom";

const DmHistoryList = ({ dmHistoryList }) => {
  const navigate = useNavigate();

  const handleMoveToDm = (dmId) => {
    navigate(`/${dmId}/dm`);
  };

  return (
    <div className={s.wrapper}>
      <h3 className={s.title}>다이렉트 메세지</h3>
      <div className={s.dmHistoryList}>
        {dmHistoryList && dmHistoryList.map((dm) => (
          <div
            key={dm.dmId}
            className={s.dmItem}
            onClick={() => handleMoveToDm(dm.dmId)}
          >
            <div className={s.dmRoomIcon}>
              <RiGroupFill className={s.groupIcon} />
            </div>
            <div className={s.container}>
              <div className={s.dmBox}>
                <h3 style={{ fontWeight: "600" }}>{dm.name}</h3>
              </div>
            </div>
          </div>
        ))}
      </div>
      {/* <h4 className={s.count}>총 {dmHistoryList.length}명</h4> */}
    </div>
  );
};

export default DmHistoryList;
