import s from "./CreateServer.module.css";
// import useUserStore from "../../../../actions/useUserStore";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
// import useAxios from "../../../../utils/instance";
import useServerData from "../../../../hooks/useServerData";
import axios from "axios";

function CreateServer() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");
  const [profileImage, setProfileImage] = useState(null);
  const navigate = useNavigate();
  // const axios = useAxios();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // const id = useUserStore.getState().user.userId; //userId from userData
      const id = 1; //test용
      const formData = new FormData();
      formData.append(
        "requestDto",
        JSON.stringify({ id, name, description, category })
      );
      if (profileImage) {
        //프로필 이미지: 선택
        formData.append("profile", profileImage);
      }
      console.log("name:", name);
      console.log("description:", description);
      console.log("category:", category);
      for (const [key, value] of formData.entries()) {
        console.log(key, value);
      }
      const response = await axios.post("/api/community/server", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        method: "POST",
        body: formData,
        redirect: "follow",
      });
      console.log(response.status);
      if (response.status == 200) {
        const serverId = response.data.serverId;
        console.log(serverId);
        //customHook으로 서버 정보 읽기
        const { defaultChannelInfo } = useServerData(
          serverId,
          id
        ).serverChannels; //첫번째 기본 채널 정보를 불러온다.
        const defaultChannelId = defaultChannelInfo[0].channelId; //default 채널의 id를 뽑아
        navigate(`/planet/${serverId}/${defaultChannelId}`); //라우팅 시킴.
      } else {
        console.log("행성 만들기 실패.");
      }
    } catch (error) {
      console.error("데이터 post 에러:", error);
    }
  };

  return (
    <>
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
        <button className={s.createBtn} type="submit" onClick={handleSubmit}>
          행성 만들기
        </button>
      </form>
    </>
  );
}
export default CreateServer;
