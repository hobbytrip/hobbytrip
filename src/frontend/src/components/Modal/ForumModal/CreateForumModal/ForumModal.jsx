import React, { useState, useRef } from "react";
import axios from "axios";
import API from "../../../../utils/API/TEST_API";
import { axiosInstance } from "../../../../utils/axiosInstance";
import s from "./ForumModal.module.css";
import { FiSearch } from "react-icons/fi";
import { LuImagePlus } from "react-icons/lu";
import { AiFillTag, AiFillCloseCircle } from "react-icons/ai";
import { useParams } from "react-router-dom";
import useUserStore from "../../../../actions/useUserStore";

function ForumModal() {
  const { serverId, channelId } = useParams();
  const { userId, nickname } = useUserStore();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [category, setCategory] = useState("CHALLENGE66");
  const [uploadedFiles, setUploadedFiles] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [isFileUploaded, setIsFileUploaded] = useState(false);
  const [filePreviews, setFilePreviews] = useState([]);
  const fileInputRef = useRef();
  const categories = [
    { name: "🔥66챌린지", value: "CHALLENGE66" },
    { name: "🍽️식단 인증", value: "FOOD" },
    { name: "💪오운완", value: "TODAY" },
    { name: "🌞미라클모닝", value: "MIRACLE" },
    { name: "🏋️‍♀칼로리챌린지", value: "CALORIE" },
    { name: "🚶‍♀️만보챌린지", value: "MANBO" },
  ];

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
  };

  const handleContentChange = (event) => {
    setContent(event.target.value);
  };

  const handleCategoryChange = (catValue) => {
    setCategory(catValue);
  };

  const handleFileChange = (event) => {
    const files = [...event.target.files];
    setUploadedFiles(files);
    setIsFileUploaded(true);

    const previews = files.map((file) => URL.createObjectURL(file));
    setFilePreviews(previews);
  };

  const handleRemoveAll = () => {
    setModalOpen(false);
    setTitle("");
    setContent("");
    setCategory("CHALLENGE66");
    setUploadedFiles([]);
    setIsFileUploaded(false);
    setFilePreviews([]);
  };

  const createNewForum = async () => {
    try {
      const formData = new FormData();
      const requestDto = {
        userId: userId,
        serverId: serverId,
        channelId: channelId,
        title: title,
        content: content,
        forumCategory: category,
      };
      console.error(requestDto);
      const jsonMsg = JSON.stringify(requestDto);
      const req = new Blob([jsonMsg], { type: "application/json" });
      formData.append("requestDto", req);
      uploadedFiles.forEach((file) => {
        formData.append("files", file);
      });

      const response = await axiosInstance.post(API.CUD_FORUM, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      console.error("포럼 생성 완료", response.data);
    } catch (error) {
      console.error("포럼 생성 에러", error);
      throw new Error("포럼 생성 에러");
    }
  };

  const toggleModal = () => {
    setModalOpen(!modalOpen);
  };
  const handleImageUploadClick = () => {
    fileInputRef.current.click();
  };

  return (
    <div className={s.wrapper}>
      {modalOpen && (
        <div className={s.forumModal}>
          <div className={s.top}>
            <AiFillCloseCircle
              className={s.removeAll}
              onClick={handleRemoveAll}
              title="모두 지우기"
            />
            <div className={s.inputs}>
              <input
                type="text"
                value={title}
                onChange={handleTitleChange}
                placeholder="제목"
                className={s.titleInput}
              />
              <textarea
                value={content}
                onChange={handleContentChange}
                placeholder="메시지를 입력하세요.."
                className={s.contentArea}
              />
            </div>
            <div className={s.imageArea}>
              {!isFileUploaded && (
                <div>
                  <input
                    type="file"
                    multiple
                    onChange={handleFileChange}
                    ref={fileInputRef}
                    style={{ display: "none" }}
                  />
                  <LuImagePlus
                    className={s.imageUploadButton}
                    onClick={handleImageUploadClick}
                  />
                </div>
              )}
              {isFileUploaded && (
                <div className={s.imagePreviews}>
                  {filePreviews.map((preview, index) => (
                    <img
                      key={index}
                      style={{ width: "80%" }}
                      src={preview}
                      alt={`${index}`}
                    />
                  ))}
                </div>
              )}
            </div>
          </div>
          <div className={s.categoryButtons}>
            <AiFillTag
              style={{
                color: "#0000008e",
                marginRight: "2px",
                fontSize: "20px",
              }}
            />
            {categories.map((cat) => (
              <button
                key={cat.value}
                onClick={() => handleCategoryChange(cat.value)}
                className={`${s.categoryButton} ${
                  category === cat.value ? s.activeCategory : ""
                }`}
              >
                {cat.name}
              </button>
            ))}
          </div>
          <div className={s.forumModalButtons}>
            <button onClick={createNewForum} className={s.newPost}>
              포스트 생성
            </button>
          </div>
        </div>
      )}
      {!modalOpen && (
        <div className={s.searchContainer}>
          <FiSearch className={s.searchIcon} />
          <input
            type="text"
            className={s.searchBar}
            placeholder="포럼 검색하기"
          />
          <button className={s.newPostButton} onClick={toggleModal}>
            새 포스트
          </button>
        </div>
      )}
    </div>
  );
}

export default ForumModal;
