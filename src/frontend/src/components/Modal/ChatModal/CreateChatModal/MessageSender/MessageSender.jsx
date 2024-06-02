import React, { useState, useRef } from "react";
import s from "./MessageSender.module.css";
import { IoSend } from "react-icons/io5";
import { FaCirclePlus } from "react-icons/fa6";
import API from "../../../../../utils/API/API";
import ChatMessage from "../../ChatMessage/ChatMessage";

const MessageSender = ({
  onMessageSend,
  serverId,
  channelId,
  writer,
  client,
}) => {
  const [chatMessage, setChatMessage] = useState("");
  const [uploadedFile, setUploadedFile] = useState(null);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isTyping, setIsTyping] = useState(false);
  const typingTimeoutRef = useRef(null);
  const fileInputRef = useRef(null);

  const sendMessage = () => {
    if (chatMessage === "") {
      return;
    }
    onMessageSend(chatMessage, uploadedFile); // 채팅 메시지 전송
    setChatMessage(""); // 메시지를 전송한 후 input창 비우기
    setUploadedFile(null); // 파일 정보 초기화
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
        destination: API.IS_TYPING,
        body: JSON.stringify({
          serverId: serverId,
          channelId: channelId,
          writer: writer,
        }),
      });
    } catch (error) {
      console.error("타이핑 중 에러:", error);
    }
  };

  const handleFileUpload = async (event) => {
    const selectedFile = event.target.files[0];
    setUploadedFile(selectedFile);
    console.error(selectedFile);
  };

  const clickFileUploadButton = (event) => {
    event.preventDefault();
    fileInputRef.current.click();
  };

  return (
    <div className={s.wrapper}>
      <div>
        {uploadedFile && (
          <div className={s.previewBox}>
            <div className={s.filePreview}>
              <img
                src={URL.createObjectURL(uploadedFile)}
                alt={uploadedFile.name}
                className={s.previewImg}
              />
              <h5 style={{ color: "#434343", fontWeight: "600" }}>
                {uploadedFile.name}
              </h5>
            </div>
          </div>
        )}
        {isDropdownOpen && !uploadedFile && (
          <div className={s.dropdownContent}>
            <button
              onClick={clickFileUploadButton}
              style={{
                backgroundColor: "transparent",
                marginTop: "5px",
                marginBottom: "5px",
              }}
            >
              <h4 style={{ color: "white", fontWeight: "400" }}>
                📁 파일 업로드
              </h4>
            </button>
            <input
              type="file"
              ref={fileInputRef}
              onChange={handleFileUpload}
              style={{ display: "none" }}
              accept=".jpg,.jpeg,.png,.gif"
            />
            <button
              onClick={clickFileUploadButton}
              style={{
                backgroundColor: "transparent",
                marginTop: "5px",
                marginBottom: "5px",
              }}
            >
              <h4 style={{ color: "white", fontWeight: "400" }}>
                ⚡스레드 만들기
              </h4>
            </button>
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

export default MessageSender;
