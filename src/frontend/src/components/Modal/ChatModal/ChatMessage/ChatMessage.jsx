import React, { useState, useEffect, useRef } from "react";
import s from "./ChatMessage.module.css";
import { FaTrashAlt } from "react-icons/fa";
import { FaDownload } from "react-icons/fa6";
import { MdEdit } from "react-icons/md";
import testImg from "../../../../assets/image/default-logo.png";
import useFormatDate from "../../../../hooks/useFormatDate";
import axios from "axios";

// 채팅 메세지를 뿌려주는 컴포넌트
// 수정,삭제 함수 포함
const ChatMessage = ({ message, onModifyMessage, onDeleteMessage }) => {
  const formattedDate = useFormatDate(message.createdAt);
  const [isEditing, setIsEditing] = useState(false);
  const [newContent, setNewContent] = useState(message.content);
  const [isDownLoading, setIsDownloading] = useState(false);
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
    if (message.files && message.files.length > 0) {
      // 파일이 첨부된 경우
      return (
        <div className={s.msgBox}>
          <img src={testImg} className={s.profileImg} alt="profile-image" />
          <div className={s.msgRightContainer}>
            <div className={s.msgTopInfos}>
              <h3 className={s.msgWriter}>{message.writer}</h3>
              <h4 className={s.msgDate}>{formattedDate}</h4>
            </div>
            {message.files.map((file, index) => (
              <div key={index} className={s.fileContainer}>
                {isImageFile(file.originalFilename) ? (
                  <img
                    src={file.fileUrl}
                    className={s.imgContent}
                    alt={`file-${index}`}
                    style={{ width: "150px" }}
                  />
                ) : (
                  <div className={s.fileDownload}>
                    <h4>{file.originalFilename}</h4>
                    <FaDownload
                      className={s.downloadIcon}
                      onClick={() =>
                        handleFileDownload(
                          file.storeFileName,
                          file.originalFilename
                        )
                      }
                    />
                    {/* <a
                    href={file.fileUrl}
                    download={file.originalFilename}
                    target="_blank"
                    rel="noopener noreferrer"
                    className={s.fileLink}
                  >
                    
                  </a> */}
                  </div>
                )}
              </div>
            ))}
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
    } else {
      // 파일이 첨부되지 않은 경우
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

  const isImageFile = (fileName) => {
    const imageExtensions = [".jpg", ".jpeg", ".png", ".gif"];
    const extension = fileName
      .substring(fileName.lastIndexOf("."))
      .toLowerCase();
    return imageExtensions.includes(extension);
  };

  const handleFileDownload = (storeFileName, originalFilename) => {
    setIsDownloading(true);
    const api = `https://fittrip.site/api/chat/file?storeFileName=${storeFileName}&originalFileName=${originalFilename}`;
    const token = localStorage.getItem("accessToken");
    axios

      .get(api, {
        responseType: "blob",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log("response", response.data);
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement("a");
        a.href = url;
        a.download = originalFilename || "download";
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
        setIsDownloading(false);
      })
      .catch((error) => {
        console.error("파일 다운로드 오류:", error);
        setIsDownloading(false);
      });
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
