import style from "./CreateCategory.module.css";
import { useState } from "react";
// import axios from "axios";
import { axiosInstance } from "../../../../../utils/axiosInstance";
import { HiHome } from "react-icons/hi2";
import useServerStore from "../../../../../actions/useServerStore";
import API from "../../../../../utils/API/API";

const CATEGORY_URL = API.COMM_CATEGORY;

function CreateCategory({ userId, onClose }) {
  const [name, setName] = useState("");
  const { serverData, setServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    setServerData: state.setServerData,
  }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (name === "") {
      alert("카테고리 이름을 입력해주세요");
      return;
    }
    try {
      const data = {
        userId: userId,
        serverId: serverData.serverInfo.serverId,
        name: name,
      };
      console.error("body:", data);

      const res = await axiosInstance.post(CATEGORY_URL, data);
      console.error(res.data);

      if (res.data.success) {
        console.log(res);
        const updatedCategories = [
          ...(serverData.serverCategories || []),
          res.data.data,
        ];
        setServerData({ ...serverData, serverCategories: updatedCategories });
        onClose();
      } else {
        console.log("카테고리 만들기 실패.");
        console.log(res);
      }
    } catch (error) {
      console.error("데이터 post 에러:", error);
    }
  };

  return (
    <>
      <form className={style.formWrapper} onSubmit={handleSubmit}>
        <div className={style.topContainer}>
          <HiHome />
          <h3>
            <b> 마을 만들기 </b>
          </h3>
        </div>
        <div className={style.name}>
          <div className={style.label}>
            <h4> 이름 </h4>
          </div>
          <div className={style.inputContainer}>
            <input
              type="text"
              value={name}
              placeholder="이름 입력"
              onChange={(e) => setName(e.target.value)}
            />
          </div>
        </div>

        <div className={style.createContainer}>
          <button className={style.createBtn} type="submit">
            마을 만들기
          </button>
        </div>
      </form>
    </>
  );
}

export default CreateCategory;
