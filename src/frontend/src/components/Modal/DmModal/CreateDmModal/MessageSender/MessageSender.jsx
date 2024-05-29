import React, { useState, useRef } from "react";
import s from "./MessageSender.module.css";
import { IoSend } from "react-icons/io5";
import { FaCirclePlus } from "react-icons/fa6";
import FileUpload from "../../Common/ChatRoom/FileUpload";
import API from "../../../../../utils/chatApi";

const DmMessageSender = ({ onMessageSend, roomId, writer, client }) => {
  const [chatMessage, setChatMessage] = useState("");
  const [uploadedFileUrl, setUploadedFileUrl] = useState(null);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isTyping, setIsTyping] = useState(false);
  const typingTimeoutRef = useRef(null);

  const sendMessage = () => {
    if (chatMessage === "") {
      return;
    }
    onMessageSend(chatMessage, uploadedFileUrl);
    setChatMessage(""); // 메시지를 전송한 후 input창 비우기
    setUploadedFileUrl(null); // 파일 URL 초기화
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      sendMessage();
    }
  };

  const toggleDropdown = () => {
    setIsDropdownOpen((prevState) => !prevState); // 드롭다운 메뉴 표시 여부
  };

  const handleTyping = () => {
    clearTimeout(typingTimeoutRef.current);
    notifyTyping();
    setIsTyping(true);
    typingTimeoutRef.current = setTimeout(() => {
      setIsTyping(false);
    }, 2000);
  };

  const notifyTyping = () => {
    try {
      client.publish({
        destination: API.SUBSCRIBE_DM(roomId),
        body: JSON.stringify({
          dmRoomId: roomId,
          writer: writer,
        }),
      });
    } catch (error) {
      console.error("타이핑 중 에러:", error);
    }
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
            <FileUpload
              onFileUpload={(fileUrl) => setUploadedFileUrl(fileUrl)}
            />
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
          onChange={(e) => {
            setChatMessage(e.target.value);
            handleTyping();
          }}
          onKeyDown={handleKeyDown}
          placeholder="메세지 보내기"
        />
        <IoSend className={s.btn} onClick={sendMessage} />
      </div>
    </div>
  );
};

export default DmMessageSender;
