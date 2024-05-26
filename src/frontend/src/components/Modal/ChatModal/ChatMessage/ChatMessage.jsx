// ChatMessage.js
import React from "react";
import s from "./ChatMessage.module.css";
import useFormatDate from "../../../../hooks/useFormatDate";
import testImg from "../../../../assets/image/default-logo.png";
const ChatMessage = ({ message }) => {
  const formattedDate = useFormatDate(message.createdAt);

  return (
    <div className={s.msgBox}>
      <img src={testImg} className={s.profileImg} alt="profile-image" />
      <div className={s.msgRightContainer}>
        <div className={s.msgTopInfos}>
          <h3 className={s.msgWriter}>{message.writer}</h3>
          <h4 className={s.msgDate}>{formattedDate}</h4>
        </div>
        <h4 className={s.msgContent}>{message.content}</h4>
      </div>
    </div>
  );
};

export default ChatMessage;
