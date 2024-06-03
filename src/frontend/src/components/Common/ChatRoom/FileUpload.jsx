import React, { useRef } from "react";
import { useParams } from "react-router-dom";
import { axiosInstance } from "../../../utils/axiosInstance";

const FileUpload = ({ onFileUpload, api, userId, writer }) => {
  const { serverId, channelId } = useParams();
  const fileInputRef = useRef(null);
  const uploadFile = async (file, requestDatas) => {
    try {
      const formData = new FormData();

      formData.append("file", file);
      Object.keys(requestDatas).forEach((key) => {
        formData.append(key, requestDatas[key]);
      });

      const response = await axiosInstance.post(api, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      return response.data.data.files;
    } catch (error) {
      console.error("파일 업로드 실패", error);
      throw new Error("파일 업로드 실패");
    }
  };

  const handleFileChange = async (event) => {
    const selectedFile = event.target.files[0];
    try {
      const requestDatas = {
        serverId,
        channelId,
        userId,
        parentId: 0,
        profileImage: "ho",
        writer,
        content,
      };
      const fileInfo = await uploadFile(selectedFile, requestDatas);
      onFileUpload(fileInfo);
    } catch (error) {
      console.error("파일 업로드 실패", error);
    }
  };

  const handleClickUploadButton = () => {
    fileInputRef.current.click();
  };

  return (
    <div>
      <button
        onClick={handleClickUploadButton}
        style={{
          backgroundColor: "transparent",
          marginTop: "5px",
          marginBottom: "5px",
        }}
      >
        <h4 style={{ color: "black" }}>📁 파일 업로드</h4>
      </button>
      <input
        type="file"
        ref={fileInputRef}
        style={{ display: "none" }}
        onChange={handleFileChange}
      />
    </div>
  );
};

export default FileUpload;
