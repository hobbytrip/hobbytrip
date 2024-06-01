import s from "./CreateServer.module.css";
import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import useServerStore from "../../../../../actions/useServerStore";
import { TbCameraPlus } from "react-icons/tb";
import API from "../../../../../utils/API/API";
import JoinServer from "../JoinServer/JoinServer"

function CreateServer() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");
  const [profileImage, setProfileImage] = useState(null);
  const [profilePreview, setProfilePreview] = useState(null);
  const setServerData = useServerStore((state) => state.setServerData);
  const imgRef = useRef();
  const nav = useNavigate();
  const [showJoinServer, setShowJoinServer] = useState(false); // 추가
  const userId = 1; // test용

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (name === '') {
      alert("행성 이름을 적어주세요");
      return;
    }
    try {
      const formData = new FormData();
      const data = JSON.stringify({
        userId: userId,
        name: name,
        // description: description,
        // category: category,
      });
      const communityData = new Blob([data], { type: "application/json" });
      formData.append("requestDto", communityData);
      if (profileImage) {
        formData.append("profile", profileImage);
      }

      const response = await axios.post(API.SERVER, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      if (response.data.success) {
        console.log(response);
        setServerData({ serverInfo: response.data.data });
        const serverId = response.data.data.serverId;
        nav(`/${serverId}/menu`);
      } else {
        console.log("행성 만들기 실패.");
      }
    } catch (error) {
      console.error("데이터 post 에러:", error);
    }
  };

  const handleImage = () => {
    const file = imgRef.current.files[0];
    if (file) {
      setProfileImage(file);
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
        setProfilePreview(reader.result);
      };
    }
  };

  return (
    <>
    {showJoinServer ? (
      <JoinServer userId={userId} onClose={() => setShowJoinServer(false)} />
    ) : (
      <form className={s.formWrapper} onSubmit={handleSubmit}>
        <div className={s.topFormContainer}>
          <div className={s.titleLabel}>🚀행성 만들기</div>
        </div>

        <div className={s.formContainer}>
          <h4 className={s.label}>
            행성 이름<a>*</a>
          </h4>
          <input
            type="text"
            value={name}
            placeholder="행성 이름 입력"
            className={s.inputBox}
            onChange={(e) => setName(e.target.value)}
          />
        </div>
        <div className={s.formContainer}>
          <h4 className={s.label}>행성 소개</h4>
          <input
            type="text"
            value={description}
            placeholder="행성 소개를 작성해주세요."
            className={s.inputBox}
            onChange={(e) => setDescription(e.target.value)}
          />
        </div>
        <div className={s.formContainer}>
          <h4 className={s.label}>카테고리/분야</h4>
          <input
            type="text"
            value={category}
            placeholder="ex.헬스, 수영, 탁구"
            className={s.inputBox}
            onChange={(e) => setCategory(e.target.value)}
          />
        </div>
        <div className={s.formContainer}>
          <h4 className={s.label}> 행성 아이콘 </h4>
          <div className={s.addImg}>
            <div>
              {profilePreview ? <img src={profilePreview} alt="profile preview" /> : null}
            </div>
            <label className={s.addImgBtn}>
              <h4>이미지 업로드</h4>
              <input
                type="file"
                ref={imgRef}
                style={{ display: 'none', border:'1px' }}
                onChange={handleImage}
              />
              <TbCameraPlus style={{ width: '15px', height: '15px' }} />
            </label>
          </div>
        </div>
        <button className={s.createBtn} type="submit">
          행성 만들기
        </button>
        <div>
          { showJoinServer ? (null) : (
            <h5 onClick={() => setShowJoinServer(true)}>
              초대 코드가 있으신가요? 
            </h5>
          )}
        </div>
      </form>
    )}
    </>
  );
}

export default CreateServer;
