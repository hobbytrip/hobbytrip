import style from "./CreateCategory.module.css";
import { useState } from "react";
import axios from "axios";
import useServerStore from "../../../../../actions/useServerStore";

const URL = 'http://localhost:8080'; // URL 정의

function CreateCategory({ userId, onClose }) { // onClose 함수를 props로 받음
  const [name, setName] = useState("");
  const { serverData, setServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    setServerData: state.setServerData
  }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    if(name === ''){
      alert("카테고리 이름을 입력해주세요");
      return;
    }
    try {
      const data = {
        userId: userId,
        serverId: serverData.serverInfo.serverId,
        name: name
      };

      const res = await axios.post(`${URL}/category`, data);

      if (res.data.success) {
        console.log(res);
        const updatedCategories = [...(serverData.serverCategories || []), res.data.data];
        setServerData({ ...serverData, serverCategories: updatedCategories });
        onClose(); // 모달 닫기
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
          <h3><b> 카테고리 만들기 </b></h3>
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
