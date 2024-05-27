import s from "./CreateServer.module.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import useServerStore from "./../../../../actions/useServerStore";

const URL = 'http://localhost:8080';

function CreateServer() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");
  const [profileImage, setProfileImage] = useState(null);
  const setServerData = useServerStore((state) => state.setServerData);
  const nav = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const id = 1; // test용
      const formData = new FormData();
      const data = JSON.stringify({
        userId: id,
        name: name,
        description: description,
        category: category,
      });
      const communityData = new Blob([data], { type: "application/json" });
      formData.append("requestDto", communityData);
      if (profileImage) {
        formData.append("profile", profileImage);
      }

      const response = await axios.post(`${URL}/server`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      if (response.status === 200) {
        console.log(response);
        const serverId = response.data.data.serverId;
        setServerData({ serverInfo: response.data.data });
        nav(`/server/${serverId}/menu`);
      } else {
        console.log("행성 만들기 실패.");
      }
    } catch (error) {
      console.error("데이터 post 에러:", error);
    }
  };

  return (
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
      <button className={s.createBtn} type="submit">
        행성 만들기
      </button>
    </form>
  );
}

export default CreateServer;
