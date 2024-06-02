import { useNavigate, useParams } from "react-router-dom";
import { IoChatbubbleEllipses } from "react-icons/io5";
import { AiOutlineClose } from "react-icons/ai";
import s from "./ForumType.module.css";
export default function ChatChannelInfo() {
  const { channelId } = useParams();
  const navigate = useNavigate();

  const onClickExitBtn = () => {
    navigate(-1); //이전 페이지로 이동
  };
  return (
    <div className={s.wrapper}>
      <div className={s.container}>
        {/* <IoChatbubbleEllipses className={s.chatIcon} /> */}
        <h3>🔥66 챌린지</h3>
      </div>
      <AiOutlineClose onClick={onClickExitBtn} />
    </div>
  );
}
