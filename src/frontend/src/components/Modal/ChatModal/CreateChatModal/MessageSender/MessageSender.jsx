import React, { useState, useRef } from "react";
import s from "./MessageSender.module.css";
import { IoSend } from "react-icons/io5";
import { FaCirclePlus } from "react-icons/fa6";
import API from "../../../../../utils/API/TEST_API";

const MessageSender = ({
  onMessageSend,
  serverId,
  channelId,
  writer,
  client,
}) => {
  const [chatMessage, setChatMessage] = useState("");
  const [uploadedFiles, setUploadedFiles] = useState([]); // 여러 파일을 저장할 배열 상태 추가
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isTyping, setIsTyping] = useState(false);
  const typingTimeoutRef = useRef(null);
  const fileInputRef = useRef(null);

  const sendMessage = () => {
    if (chatMessage === "" && uploadedFiles.length === 0) {
      // 채팅 메시지와 업로드된 파일이 없을 때 전송 중지
      return;
    }
    onMessageSend(chatMessage, uploadedFiles); // 채팅 메시지와 업로드된 파일들을 전송
    setChatMessage(""); // 메시지를 전송한 후 input창 비우기
    setUploadedFiles([]); // 업로드된 파일 정보 초기화
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      sendMessage();
    }
  };

  const toggleDropdown = () => {
    setIsDropdownOpen((prevState) => !prevState);
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
    const selectedFiles = event.target.files;
    let newFiles = Array.from(selectedFiles);

    const maxFileCount = 5;
    const maxFileSizeMB = 5;
    const maxFileSizeBytes = maxFileSizeMB * 1024 * 1024;

    const validFiles = [];
    const invalidFiles = [];
    newFiles.forEach((file) => {
      if (file.size <= maxFileSizeBytes) {
        validFiles.push(file);
      } else {
        invalidFiles.push(file.name);
      }
    });

    if (validFiles.length + uploadedFiles.length > maxFileCount) {
      alert(`최대 ${maxFileCount}개의 파일을 첨부할 수 있습니다.`);
    } else if (invalidFiles.length > 0) {
      alert(`파일 용량이 너무 큽니다.`);
    } else {
      setUploadedFiles((prevFiles) => [...prevFiles, ...validFiles]);
    }
  };

  const clickFileUploadButton = (event) => {
    event.preventDefault();
    fileInputRef.current.click();
  };

  return (
    <div className={s.wrapper}>
      <div>
        {uploadedFiles.length > 0 && (
          <div className={s.previewBox}>
            {uploadedFiles.map((file, index) => (
              <div key={index} className={s.filePreview}>
                <img
                  src={URL.createObjectURL(file)}
                  alt={file.name}
                  className={s.previewImg}
                />
                <h5 style={{ color: "#434343", fontWeight: "600" }}>
                  {file.name}
                </h5>
              </div>
            ))}
          </div>
        )}
        {isDropdownOpen && (
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
              multiple
              maxLength={5}
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
