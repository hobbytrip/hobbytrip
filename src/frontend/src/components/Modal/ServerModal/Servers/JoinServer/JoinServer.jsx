import style from "./JoinServer.module.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useUserStore from '../../../../../actions/useUserStore';
import useServerStore from "../../../../../actions/useServerStore";
import API from "../../../../../utils/API/API";
import { axiosInstance } from "../../../../../utils/axiosInstance";

function JoinServer({ onClose }) { 
  const [link, setLink] = useState("");
  const nav = useNavigate();
  const { serverData, setServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    setServerData: state.setServerData
  }));
  const { userId } = useUserStore();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if(link === ''){
      alert("초대 링크를 입력해주세요");
      return;
    }

    const [serverId, inviteLink] = link.split('/');
    if (!serverId || !inviteLink) {
      alert("잘못된 초대 링크 형식입니다.");
      return;
    }

    try {
      const data = {
        userId: userId,
        serverId: serverId,
        link: inviteLink
      };
      console.log(data)

      const res = await axiosInstance.post(API.JOIN_SERVER, data);

      if (res.data.success) {
        console.log(res);
        setServerData({ serverInfo: res.data.data });
        nav(`/${serverId}/menu`);
        onClose();
      } else {
        console.log(res); 
        alert(res.data.message);
      }
    } catch (error) {
      console.error("데이터 post 에러:", error);
    }
  };

  const handleClose = () => {
    onClose();
  }

  return (
    <>
      <form className={style.formWrapper} onSubmit={handleSubmit}>
        <div className={style.topFormContainer}>
          <div className={style.titleLabel}>🚀행성 입장하기</div>
        </div>

        <div className={style.formContainer}>
          <h4 className={style.label}>
            초대 링크 <a>*</a>
          </h4>
          <input
            type="text"
            value={link}
            placeholder="초대 링크 입력"
            className={style.inputBox}
            onChange={(e) => setLink(e.target.value)}
          />
        </div>
        <div className={style.createContainer}>
          <button className={style.createBtn} type="submit">
            마을 입장하기
          </button>
        </div>
        <div onClick={handleClose}>
          <h5> 행성 생성하기 </h5>
        </div>
      </form>
    </>
  );
}

export default JoinServer;
