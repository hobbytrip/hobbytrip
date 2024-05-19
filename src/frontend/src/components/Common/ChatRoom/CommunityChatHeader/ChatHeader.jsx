import s from "./ChatHeader.module.css";
import iconImg from "../../../../assets/image/logo-white.png";
import { AiOutlineMenu } from "react-icons/ai";

export default function ChatHeader() {
  return (
    <div className={s.ChatHeaderContainer}>
      <img src={iconImg} className={s.iconImg} alt="logo-img" />
      <AiOutlineMenu className={s.chatCloseIcon} />
    </div>
  );
}
