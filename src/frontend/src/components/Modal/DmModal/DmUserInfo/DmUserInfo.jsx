import { useParams } from "react-router-dom";
import { AiOutlineUserAdd } from "react-icons/ai";
import { GiSpaceship } from "react-icons/gi";
import { FaCircle } from "react-icons/fa";
import s from "./DmUserInfo.module.css";

export default function ChatChannelInfo() {
  const { channelId } = useParams();
  return (
    <div className={s.wrapper}>
      <div className={s.leftSection}>
        <GiSpaceship className={s.leftIcon} />
        <FaCircle className={s.state} />
        <div className={s.userName}>
          <h3>일반</h3>
        </div>
      </div>
      <AiOutlineUserAdd className={s.chatIcon} />
    </div>
  );
}
