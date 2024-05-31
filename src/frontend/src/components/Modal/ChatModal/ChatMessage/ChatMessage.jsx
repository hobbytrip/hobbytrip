import React, { useState, useEffect, useRef } from "react";
import s from "./ChatMessage.module.css";
import { FaTrashAlt } from "react-icons/fa";
import { MdEdit } from "react-icons/md";
import testImg from "../../../../assets/image/default-logo.png";
import useFormatDate from "../../../../hooks/useFormatDate";

// 채팅 메세지를 뿌려주는 컴포넌트
// 수정,삭제 함수 포함
const ChatMessage = ({ message, onModifyMessage, onDeleteMessage }) => {
  const formattedDate = useFormatDate(message.createdAt);
  const [isEditing, setIsEditing] = useState(false);
  const [newContent, setNewContent] = useState(message.content);
  const inputRef = useRef(null);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleSave = () => {
    onModifyMessage(message.messageId, newContent);
    setIsEditing(false);
  };

  const handleDeleteClick = () => {
    onDeleteMessage(message.messageId);
  };

  const handleCancel = () => {
    setIsEditing(false);
    setNewContent(message.content);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleSave();
    } else if (e.key === "Escape") {
      handleCancel();
    }
  };

  useEffect(() => {
    if (isEditing && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isEditing]);

  const renderMessageContent = () => {
    // 이미지 파일
    if (message.files) {
      return (
        <div className={s.msgBox}>
          <img src={testImg} className={s.profileImg} alt="profile-image" />
          <div className={s.msgRightContainer}>
            <div className={s.msgTopInfos}>
              <h3 className={s.msgWriter}>{message.writer}</h3>
              <h4 className={s.msgDate}>{formattedDate}</h4>
            </div>
            <img
              src={message.files[0].fileUrl}
              className={s.msgContent}
              alt="uploadedFile"
            />
          </div>
        </div>
      );
    } else {
      // 일반 텍스트 메세지
      return (
        <div className={s.msgBox}>
          <img src={testImg} className={s.profileImg} alt="profile-image" />
          <div className={s.msgRightContainer}>
            <div className={s.msgTopInfos}>
              <h3 className={s.msgWriter}>{message.writer}</h3>
              <h4 className={s.msgDate}>{formattedDate}</h4>
            </div>
            {isEditing ? (
              <div>
                <input
                  type="text"
                  ref={inputRef}
                  value={newContent}
                  onChange={(e) => setNewContent(e.target.value)}
                  onKeyDown={handleKeyDown}
                  className={s.msgEdit}
                />
                <h5
                  style={{
                    fontWeight: "400",
                    marginTop: "5px",
                    marginLeft: "2px",
                  }}
                >
                  {" "}
                  Enter키로 <a style={{ fontSize: "10px" }}>
                    저장
                  </a> Esc키로 <a style={{ fontSize: "10px" }}>취소</a>
                </h5>
              </div>
            ) : (
              <h4 className={s.msgContent}>{message.content}</h4>
            )}
          </div>
        </div>
      );
    }
  };

  return (
    <div className={s.chatWrapper}>
      <div className={s.modals}>
        {!message.fileUrl && (
          <MdEdit
            onClick={handleEditClick}
            style={{ width: "25px", color: "#434343", cursor: "pointer" }}
          />
        )}
        <FaTrashAlt
          onClick={handleDeleteClick}
          style={{ width: "25px", color: "#434343", cursor: "pointer" }}
        />
      </div>
      {renderMessageContent()}
    </div>
  );
};

export default ChatMessage;
