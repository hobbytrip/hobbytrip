import { useParams } from "react-router-dom";
import { AiOutlineUserAdd } from "react-icons/ai";
import s from "./DmUserInfo.module.css";
export default function ChatChannelInfo() {
  const { channelId } = useParams();
  return (
    <div className={s.wrapper}>
      <div className={s.userName}>
        <h3>수영하는 우주인</h3>
      </div>
      <AiOutlineUserAdd className={s.chatIcon} />
      
    </div>
  );
}
