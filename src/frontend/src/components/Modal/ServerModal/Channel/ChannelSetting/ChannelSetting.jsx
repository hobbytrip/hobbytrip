import style from "./ChannelSetting.module.css";
import { useState, useEffect } from "react";
import useServerStore from "../../../../../actions/useServerStore";
import API from "../../../../../utils/API/API";
import { axiosInstance } from "../../../../../utils/axiosInstance";
import {  HiUserGroup } from "react-icons/hi2";

function ChannelSetting({ userId, channel, onClose }) { 
  const [name, setName] = useState("");
  const { setServerData } = useServerStore((state) => ({
    setServerData: state.setServerData
  }));
  const { serverData } = useServerStore.getState();
  const channelId = channel.channelId;
  const categoryId = channel.categoryId;

  useEffect(() => {
    const fetchChannel = async () => {
      try {
        const channel = serverData.serverChannels.find(c => c.channelId === channelId);
        if (channel) {
          setName(channel.name); 
        } else {
          console.error("channel not found.");
        }
      } catch (error) {
        console.error("Error fetching channel data:", error);
      }
    };
  
    fetchChannel();
  }, [channelId, serverData.serverChannels]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if(name === ''){
      alert("채널 이름을 입력해주세요");
      return;
    }
    if (String(userId) !== String(serverData.serverInfo.managerId)) {
      alert("수정 권한이 없습니다");
      return;
    }
    try {
      const data = {
        userId: userId,
        serverId: serverData.serverInfo.serverId,
        categoryId: categoryId,
        channelId: channelId,
        name: name
      };

      const res = await axiosInstance.patch(API.COMM_CHANNEL, data);

      if (res.data.success) {
        console.log(res);
        const updatedChannel = res.data.data;
        const updatedChannels = serverData.serverChannels.map(channel => {
          if (channel.channelId === updatedChannel.channelId) {
            return updatedChannel; 
          }
          return channel;
        });
        setServerData({serverChannels: updatedChannels});
        onClose();
      } else {
        console.log("채널 수정 실패.");
        console.log(res); 
      }
    } catch (error) {
      console.error("데이터 patch 에러:", error);
    }
  };

  const handleDelete = async () => {
    const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
    if (confirmDelete) {
      if (String(userId) !== String(serverData.serverInfo.managerId)) {
        alert("삭제 권한이 없습니다");
      } else {
        try {
          const res = await axiosInstance.delete(API.COMM_CHANNEL, {
            data: {
              serverId: serverData.serverInfo.serverId,
              userId: userId,
              channelId: channelId
            },
          });
          if (res.status === 200) {
            alert("삭제되었습니다");
            const updatedChannels = serverData.serverChannels.filter(c => c.channelId !== channelId);
            setServerData({serverChannels: updatedChannels});
          } else {
            alert("삭제하는 중에 오류가 발생했습니다");
            console.error(res);
          }
        } catch (error) {
          alert("삭제하는 중에 오류가 발생했습니다");
          console.error(error);
        }
      }
    }
  }

  return (
    <>
    <div className={style.wrapper}>
      <form className={style.formWrapper} onSubmit={handleSubmit}>
        <div className={style.topContainer}>
          <h3>
            <b>
              <HiUserGroup style={{marginRight:'5px'}} />
              채널 설정          
            </b>
          </h3>
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
        
        <div className={style.createContainer} >
          <button className={style.createBtn} 
            style={{backgroundColor: 'var(--main-purple)', color:'white'}}
            type="submit">
            저장하기
          </button>
        </div>
        
        <button onClick={handleDelete}>
          <h5>삭제하기</h5>
        </button>
      </form>
      <div className={style.backButtonContainer}>
        <button className={style.backButton} onClick={onClose}>
        <h4 style={{ color: "#fff", textDecoration: "underline" }}>
          뒤로 가기
        </h4>
        </button>
      </div>
    </div>
    </>
  );
}

export default ChannelSetting;
