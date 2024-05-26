import React, { useState } from "react";
import s from "./MessageSender.module.css";
import { IoSend } from "react-icons/io5";
import { FaCirclePlus } from "react-icons/fa6";
import FileUpload from "../FileUpload";

const MessageSender = ({ onMessageSend, onFileUpload }) => {
  const [chatMessage, setChatMessage] = useState("");
  const [uploadedFileUrl, setUploadedFileUrl] = useState(null);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const sendMessage = () => {
    if (chatMessage === "") {
      return;
    }
    onMessageSend(chatMessage);
    setChatMessage("");
  };
  const toggleDropdown = () => {
    setIsDropdownOpen((prevState) => !prevState); // 드롭다운 메뉴 표시 여부
  };

  return (
    <div className={s.wrapper}>
      {uploadedFileUrl && (
        <div className={s.filePreview}>
          <img src={uploadedFileUrl} alt="첨부된 파일" />
        </div>
      )}
      <div>
        {isDropdownOpen && (
          <div className={s.dropdownContent}>
            <FileUpload onFileUpload={onFileUpload} />
            <h4 style={{ color: "black" }}>⚡스레드 만들기</h4>
          </div>
        )}
      </div>
      <div className={s.senderContainer}>
        <FaCirclePlus
          onClick={toggleDropdown}
          className={s.btn}
          style={{ marginRight: "10px" }}
        />
        <input
          type="text"
          value={chatMessage}
          className={s.inputContent}
          onChange={(e) => setChatMessage(e.target.value)}
          placeholder="메세지 보내기"
        />
        <IoSend className={s.btn} onClick={sendMessage} />
      </div>
    </div>
  );
};

export default MessageSender;
