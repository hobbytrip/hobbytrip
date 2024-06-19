import React, { useEffect, useState } from "react";
import s from "./DmHistoryList.module.css";
import { RiGroupFill } from "react-icons/ri";
import { useNavigate } from "react-router-dom";
import useUserStore from "../../../../actions/useUserStore";
import { axiosInstance } from "../../../../utils/axiosInstance";
import API from "../../../../utils/API/API";

const DmHistoryList = ({ dmHistoryList }) => {
  const [dmNotice, setDmNotice] = useState([]);
  const navigate = useNavigate();
  const { userId } = useUserStore();

  const handleMoveToDm = (dmId) => {
    navigate(`/${dmId}/dm`);
  };

  const getNotice = async () => {
    try {
      const res = await axiosInstance.get(API.DM_SSE_MAIN(userId));
      if (res.data.success) {
        setDmNotice(res.data.data);
        console.log('dm data:', res.data.data);
      }
    } catch (error) {
      console.error("Error fetching server notices:", error);
    }
  };

  useEffect(() => {
    getNotice();
  }, [userId]);

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
