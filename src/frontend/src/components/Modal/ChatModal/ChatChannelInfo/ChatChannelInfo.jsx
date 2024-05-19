import { useParams } from "react-router-dom";
import { IoChatbubbleEllipses } from "react-icons/io5";
import s from "./ChatChannelInfo.module.css";
export default function ChatChannelInfo() {
  const { channelId } = useParams();
  return (
    <div className={s.wrapper}>
      <IoChatbubbleEllipses className={s.chatIcon} />
      <div className={s.channelName}>
        <h3>일반</h3>
      </div>
    </div>
  );
}
