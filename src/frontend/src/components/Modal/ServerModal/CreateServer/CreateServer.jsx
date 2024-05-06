import s from "./CreateServer.module.css";
import useUserStore from "../../../../actions/useUserStore";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useAxios from "../../../../utils/instance";
import { AiOutlineClose } from "react-icons/ai";
import useServerData from "../../../../hooks/useServerData";

function CreateServer() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");
  const [profileImage, setProfileImage] = useState(null);
  const navigate = useNavigate();
  const axios = useAxios();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const id = useUserStore.getState().user.userId; //userId from userData
      // const id = 1; //test용
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
      const response = await axios.post("/community/server", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      if (response.status == 200) {
        const serverId = response.data.data.serverId;
        navigate(`/planet/${serverId}`); // 라우팅
        // useServerData(serverId, id); //customHook
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
          <h3 className={s.titleLabel}>🚀행성 만들기</h3>
          <AiOutlineClose />
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
