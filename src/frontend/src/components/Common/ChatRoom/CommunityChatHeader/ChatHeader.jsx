import s from "./ChatHeader.module.css";
import iconImg from "../../../../assets/image/logo-white.png";
import { AiOutlineMenu } from "react-icons/ai";
import { useNavigate } from "react-router-dom";

export default function ChatHeader() {
  const nav = useNavigate();
  return (
    <div className={s.ChatHeaderContainer}>
      <img
        src={iconImg}
        className={s.iconImg}
        alt="logo-img"
        onClick={() => {
          console.log('mainnnnnn');
          nav("/main")}}
      />
      <AiOutlineMenu className={s.menuIcon} />
    </div>
  );
}
