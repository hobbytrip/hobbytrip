import style from "./CreateChannel.module.css";
import { useState } from "react";
import { IoCheckmarkCircleOutline, IoCheckmarkCircle, IoDocuments } from "react-icons/io5";
import { HiUserGroup, HiMiniSpeakerWave } from "react-icons/hi2";
import { MdOutlineNumbers } from "react-icons/md";
import useServerStore from "../../../../../actions/useServerStore";
import API from "../../../../../utils/API/API";
import { axiosInstance } from "../../../../../utils/axiosInstance";
import useUserStore from "../../../../../actions/useUserStore";


function CreateChannel({ categoryId, onClose, onBack }) {
  const [name, setName] = useState("");
  const [type, setType] = useState("CHAT");
  const [open, setOpen] = useState(false);
  const { serverData, setServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    setServerData: state.setServerData,
  }));
  const { userId } = useUserStore();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (name === '') {
      alert("채널 이름을 입력해주세요");
      return;
    }
    try {
      const data = {
        userId: userId,
        serverId: serverData.serverInfo.serverId,
        categoryId: categoryId,
        channelType: type,
        name: name,
      };
      const res = await axiosInstance.post(API.COMM_CHANNEL, data);

      if (res.data.success) {
        console.log(res);
        const updatedChannels = [...(serverData.serverChannels || []), res.data.data];
        setServerData({ ...serverData, serverChannels: updatedChannels });
        console.log(serverData);
        onClose();

      } else {
        console.log("채널 만들기 실패.");
        console.log(res);
      }
    } catch (error) {
      console.error("데이터 post 에러:", error);
    }
  };

  const handleClose = () => {
    if(categoryId){
      onClose();
    }
    else{
      onBack();
    }
  }

  return (
    <>
      <form className={style.formWrapper} onSubmit={handleSubmit}>
        <div className={style.topContainer}>
          <HiUserGroup style={{ width: '20px', height: '20px' }} />
          <h3><b> 채널 만들기 </b></h3>
        </div>
        <div className={style.type}>
          <div className={style.label}>
            <h4> 유형 </h4>
          </div>
          <div className={style.typeSelectRadio}>
            <div
              className={style.typeRadioOption}
              onClick={() => setType("CHAT")}
            >
              <MdOutlineNumbers />
              <h4> 텍스트 </h4>
              {type === "CHAT" ? (
                <IoCheckmarkCircle className={style.purpleIcon} />
              ) : (
                <IoCheckmarkCircleOutline style={{ width: '18px', height: '18px' }} />
              )}
            </div>
            <div
              className={style.typeRadioOption}
              onClick={() => setType("VOICE")}
            >
              <HiMiniSpeakerWave />
              <h4> 음성, 화상 </h4>
              {type === 'VOICE' ? (<IoCheckmarkCircle className={style.purpleIcon} />
              ) : (
                <IoCheckmarkCircleOutline style={{ width: '18px', height: '18px' }} />
              )}
            </div>
            <div
              className={style.typeRadioOption}
              onClick={() => setType("FORUM")}
            >
              <IoDocuments />
              <h4> 포럼 </h4>
              {type === 'FORUM' ? (<IoCheckmarkCircle className={style.purpleIcon} />
              ) : (
                <IoCheckmarkCircleOutline style={{ width: '18px', height: '18px' }} />
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
            <h5> 선택한 친구들만 방을 볼 수 있어요.</h5>
          </div>
          <div onClick={() => setOpen(!open)}>
            {open ? (<IoCheckmarkCircle className={style.purpleIcon} />
            ) : (
              <IoCheckmarkCircleOutline style={{ width: '18px', height: '18px' }} />
            )}
          </div>
        </div>

        <div className={style.createContainer}>
          <button
            className={style.createBtn}
            style={{ backgroundColor: 'var(--main-purple)', color: 'white' }}
            type="submit">
            방 만들기
          </button>
        </div>
      </form>
      
      <button className={style.backBtn} onClick={handleClose} style={{color:'white'}}>
        <h4> 뒤로 가기</h4>
      </button>
    </>
  );
}

export default CreateChannel;
