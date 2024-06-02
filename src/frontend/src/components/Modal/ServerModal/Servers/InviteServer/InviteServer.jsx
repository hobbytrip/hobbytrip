import style from "./InviteServer.module.css";
import { useEffect, useState } from "react";
import { axiosInstance } from "../../../../../utils/axiosInstance";
import useServerStore from "../../../../../actions/useServerStore";
import API from "../../../../../utils/API/API";
import { IoClose } from "react-icons/io5";
import { FiSearch } from "react-icons/fi";

function InviteServer({ onClose }) {
  const [link, setLink] = useState("");
  const { serverData } = useServerStore((state) => ({
    serverData: state.serverData,
  }));
  const serverInfo = serverData.serverInfo;

  const handleClose = () => {
    onClose();
  };

  const handleCopy = () => {
    navigator.clipboard.writeText(link).then(() => {
      alert("초대 링크가 클립보드에 복사되었습니다.");
    }).catch(err => {
      console.error("클립보드 복사 실패:", err);
    });
  };

  useEffect(() => {
    const fetchInvitation = async () => {
      try {
        const res = await axiosInstance.get(API.INVITE_SERVER(serverInfo.serverId));
        setLink(`${serverInfo.serverId}/${res.data.data.invitationCode}`);
      } catch (error) {
        console.error("Invitation fetch error:", error);
      }
    };

    fetchInvitation();
  }, []);

  return (
    <>
      <div className={style.backdrop}>
        <div className={style.formWrapper}>
          <div className={style.topFormContainer}>
            <div>
              <h4>
                <span style={{ color: 'var(--main-purple)', margin: '5px' }}> {serverInfo.name} </span>
                행성으로의 초대장 보내기
              </h4>
            </div>
            <IoClose onClick={handleClose} />
          </div>

          <div className={style.searchContainer}>
            <input type="text" className={style.searchBar} placeholder="검색하기" />
            <FiSearch className={style.searchIcon} />
          </div>

          <div className={style.friendList}>
          </div>

          <div className={style.linkContainer}>
            <h6> 또는 초대장 링크 전송하기</h6>
            <div>
              <div className={style.link}> <h5> {link} </h5> </div>
              <button className={style.copyBtn} onClick={handleCopy}>
                복사
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default InviteServer;
