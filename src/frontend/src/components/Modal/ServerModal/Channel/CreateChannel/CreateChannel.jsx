import style from "./CreateChannel.module.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { IoCheckmarkCircleOutline, IoCheckmarkCircle, IoDocuments } from "react-icons/io5";
import { HiHome, HiMiniSpeakerWave } from "react-icons/hi2";
import { MdOutlineNumbers } from "react-icons/md";
// import useServerData from "../../../../hooks/useServerData";
import useServerStore from "../../../../../actions/useServerStore";

function CreateChannel() {
  const [name, setName] = useState("");
  const [type, setType] = useState("CHAT");
  const [open, setOpen] = useState(false);
  const nav = useNavigate();
  // const axios = useAxios();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const id = 1; //test용
      const serverId = 2;
      const formData = new FormData();
      formData.append(
        "requestDto",
        JSON.stringify({ userId: id, serverId, categoryId, type, name })
      );
      const response = await axios.post("http://localhost:8080/api/community/channel", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        }
      });
      if (response.status == 200) {
        const { defaultChannelInfo } = useServerStore(
          serverId,
          id
        ).serverChannels;
        const defaultChannelId = defaultChannelInfo[0].channelId;
        if (defaultChannelInfo.type === 'CHAT') {
          nav(`/planet/${serverId}/${defaultChannelId}`);
        } else if (defaultChannelInfo.type === 'VOICE') {
          nav(`/planet/${serverId}/${defaultChannelId}`);
        } else if (defaultChannelInfo.type === 'FORUM') {
          nav(`/planet/${serverId}/${defaultChannelId}`);
        }
        nav(`/planet/${serverId}/${defaultChannelId}`);
      } else {
        console.log("행성 만들기 실패.");
      }
    } catch (error) {
      console.error("데이터 post 에러:", error);
    }
  };

  return (
    <>
      <form className={style.formWrapper} onSubmit={handleSubmit}>
        <div className={style.topContainer}>
          <HiHome style={{width: '20px', height: '20px'}} className={style.purpleIcon}/>
          <h3><b> 마을 만들기 </b></h3>
        </div>
        <div className={style.type}>
          <div className={style.label}>
            <h4> 유형 </h4>
          </div>
          <div className={style.typeSelectRadio}>
            <div className={style.typeRadioOption} onClick={() => setType("CHAT")}>
              <MdOutlineNumbers />
              <h4> 텍스트 </h4>
              {type === 'CHAT' ? (
                <IoCheckmarkCircle className={style.purpleIcon} />
                ) : (
                <IoCheckmarkCircleOutline style={{width: '18px', height: '18px'}}/>
                ) }
            </div>
            <div className={style.typeRadioOption} onClick={() => setType("VOICE")}>
              <HiMiniSpeakerWave />
              <h4> 음성, 화상 </h4>
              {type === 'VOICE' ? (<IoCheckmarkCircle className={style.purpleIcon} />
                ) : (
                <IoCheckmarkCircleOutline style={{width: '18px', height: '18px'}} />
                )}
            </div>
            <div className={style.typeRadioOption} onClick={() => setType("FORUM")}>
              <IoDocuments />
              <h4> 포럼 </h4>
              {type === 'FORUM' ? (<IoCheckmarkCircle className={style.purpleIcon} />
                ) : (
                <IoCheckmarkCircleOutline style={{width: '18px', height: '18px'}} />
                )}
            </div>
          </div>
        </div>

        <div className={style.name}>
          <div className={style.label}>
            <h4> 이름 </h4>
          </div>
          <div className={style.inputContainer}>
            <input
              type="text"
              value={name}
              placeholder="이름 입력"
              onChange={(e) => setName(e.target.value)}
            />
          </div>
        </div>
        <div className={style.open}>
          <div className={style.openExplain}>
            <h4> 비공개 여부 </h4>
            <h5> 선택한 친구들만 마을을 볼 수 있어요.</h5>
          </div>
          <div onClick={() => setOpen(!open)} >
            { open ? (<IoCheckmarkCircle className={style.purpleIcon} />
              ) : (
              <IoCheckmarkCircleOutline style={{width: '18px', height: '18px'}} />
              )}
          </div>
        </div>
        
        <div className={style.createContainer}>
          <button className={style.createBtn} type="submit">
            마을 만들기
          </button>
        </div>
      </form>
    </>
  );
}

export default CreateChannel;
