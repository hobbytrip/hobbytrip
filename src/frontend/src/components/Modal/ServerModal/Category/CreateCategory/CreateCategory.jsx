import style from "./CreateCategory.module.css";
import { useState } from "react";
import { axiosInstance } from "../../../../../utils/axiosInstance";
import { HiHome } from "react-icons/hi2";
import useServerStore from "../../../../../actions/useServerStore";
import API from "../../../../../utils/API/API";
import useUserStore from "../../../../../actions/useUserStore";

function CreateCategory({ onClose, onBack }) {
  const [name, setName] = useState("");
  const { setServerData } = useServerStore((state) => ({
    setServerData: state.setServerData,
  }));
  const { serverData } = useServerStore();
  const { USER } = useUserStore();
  const userId = USER.userId;

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

      const res = await axiosInstance.post(API.COMM_CATEGORY, data);

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
      <button className={style.backBtn} onClick={onBack}>
        <h4> 뒤로 가기</h4>
      </button>
    </>
  );
}

export default CreateCategory;
