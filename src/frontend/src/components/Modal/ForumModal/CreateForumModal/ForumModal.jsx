import React, { useState, useRef } from "react";
import { useQueryClient } from "@tanstack/react-query";
import API from "../../../../utils/API/API";
import { axiosInstance } from "../../../../utils/axiosInstance";
import s from "./ForumModal.module.css";
import { FiSearch } from "react-icons/fi";
import { FaFileUpload } from "react-icons/fa";
import { AiFillTag, AiFillCloseCircle } from "react-icons/ai";
import { useParams } from "react-router-dom";
import useUserStore from "../../../../actions/useUserStore";

function ForumModal({ onNewForum }) {
  const { serverId, channelId } = useParams();
  const { userId, nickname } = useUserStore();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [category, setCategory] = useState("CHALLENGE66");
  const [uploadedFiles, setUploadedFiles] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [isFileUploaded, setIsFileUploaded] = useState(false);
  const [imagePreview, setImagePreview] = useState(null);
  const fileInputRef = useRef();
  const categories = [
    { name: "🔥66챌린지", value: "CHALLENGE66" },
    { name: "🍽️식단 인증", value: "FOOD" },
    { name: "💪오운완", value: "TODAY" },
    { name: "🌞미라클모닝", value: "MIRACLE" },
    { name: "🏋️‍♀칼로리챌린지", value: "CALORIE" },
    { name: "🚶‍♀️만보챌린지", value: "MANBO" },
  ];

  const queryClient = useQueryClient();

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
    const files = Array.from(event.target.files);
    if (files.length > 5) {
      alert("파일 첨부는 5개까지 가능합니다");
      return;
    }
    setUploadedFiles((prevFiles) => [...prevFiles, ...files]);
    setIsFileUploaded(true);

    const firstImageFile = files.find((file) => file.type.startsWith("image/"));
    if (firstImageFile) {
      const preview = URL.createObjectURL(firstImageFile);
      setImagePreview(preview);
    }
  };

  const handleRemoveAll = () => {
    setModalOpen(false);
    setTitle("");
    setContent("");
    setCategory("CHALLENGE66");
    setUploadedFiles([]);
    setIsFileUploaded(false);
    setImagePreview(null);
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

      onNewForum(response.data.data);
      queryClient.invalidateQueries(["forum", channelId, userId]);
      setModalOpen(false);
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
            <div className={s.uploadContainer}>
              <div className={s.imageArea}>
                <div className={s.imageModals}>
                  <input
                    type="file"
                    multiple
                    onChange={handleFileChange}
                    ref={fileInputRef}
                    style={{ display: "none" }}
                  />
                  {!isFileUploaded && (
                    <FaFileUpload
                      className={s.uploadButton}
                      onClick={handleImageUploadClick}
                    />
                  )}
                  {isFileUploaded && (
                    <div className={s.imagePreview}>
                      <img
                        style={{ width: "80%" }}
                        src={imagePreview}
                        alt="preview"
                      />
                    </div>
                  )}
                  <button
                    className={s.uploadModal}
                    onClick={handleImageUploadClick}
                  >
                    <h4 style={{ color: "#00000096" }}>파일 업로드</h4>
                  </button>
                </div>
              </div>
            </div>
          </div>
          {isFileUploaded && (
            <div className={s.fileNames}>
              {uploadedFiles.map((file, index) => (
                <h4
                  key={index}
                  className={s.fileName}
                  style={{ fontWeight: "300" }}
                >
                  {file.name}
                </h4>
              ))}
            </div>
          )}
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
