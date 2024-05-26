import React from "react";
import s from "./ChatMessage.module.css";
import useFormatDate from "../../../../hooks/useFormatDate";
import testImg from "../../../../assets/image/default-logo.png";

const ChatMessage = ({ message }) => {
  const formattedDate = useFormatDate(message.createdAt);

  // 업로드된 파일이 있으면
  if (message.fileUrl) {
    // 이미지 파일
    if (message.contentType && message.contentType.startsWith("image")) {
      return (
        <div className={s.msgBox}>
          <img
            src={message.fileUrl}
            className={s.profileImg}
            alt="uploaded-file"
          />
          <div className={s.msgRightContainer}>
            <div className={s.msgTopInfos}>
              <h3 className={s.msgWriter}>{message.writer}</h3>
              <h4 className={s.msgDate}>{formattedDate}</h4>
            </div>
            <img
              src={message.fileUrl}
              className={s.msgContent}
              alt="uploaded-file"
            />
          </div>
        </div>
      );
    } else {
      // 이미지 파일이 아닌 경우 다운로드 링크
      return (
        <div className={s.msgBox}>
          <img src={testImg} className={s.profileImg} alt="profile-image" />
          <div className={s.msgRightContainer}>
            <div className={s.msgTopInfos}>
              <h3 className={s.msgWriter}>{message.writer}</h3>
              <h4 className={s.msgDate}>{formattedDate}</h4>
            </div>
            <a
              href={message.fileUrl}
              target="_blank"
              rel="noopener noreferrer"
              className={s.msgContent}
            >
              {message.fileName}
            </a>
          </div>
        </div>
      );
    }
  }

  // 일반 텍스트 메시지
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
