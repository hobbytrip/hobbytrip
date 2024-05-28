import style from "./CategorySetting.module.css";
import { useState, useEffect } from "react";
import axios from "axios";
import useServerStore from "../../../../../actions/useServerStore";

const URL = 'http://localhost:8080'; // URL 정의

function CategorySetting({ userId, categoryId, onClose }) { // onClose 함수를 props로 받음
  const [name, setName] = useState("");
  const { serverData, setServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    setServerData: state.setServerData
  }));

  const serverId = serverData.serverInfo.serverId;
  useEffect(() => {
    const fetchCategory = async () => {
      try {
        const category = serverData.serverCategories.find(cat => cat.categoryId === categoryId);
        if (category) {
          setName(category.name); 
        } else {
          console.error("Category not found.");
        }
      } catch (error) {
        console.error("Error fetching category data:", error);
      }
    };
  
    fetchCategory();
  }, [categoryId, serverData.serverCategories]);

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
        categoryId: categoryId,
        name: name
      };

      const res = await axios.patch(`${URL}/category`, data);

      if (res.data.success) {
        console.log(res);
        const updatedCategory = res.data.data;
        // 기존 카테고리 배열에서 해당 categoryId와 일치하는 카테고리를 찾음
        const updatedCategories = serverData.serverCategories.map(category => {
          if (category.categoryId === updatedCategory.categoryId) {
            return updatedCategory; // 업데이트된 카테고리로 교체
          }
          return category; // 기존 카테고리 유지
        });
        setServerData({ ...serverData, serverCategories: updatedCategories });
        onClose();
      } else {
        console.log("카테고리 수정 실패.");
        console.log(res); 
      }
    } catch (error) {
      console.error("데이터 patch 에러:", error);
    }
  };

  const handleDelete = async () => {
    const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
    if (confirmDelete) {
      if (String(userId) !== String(serverData.serverInfo.managerId)) {
        alert("삭제 권한이 없습니다");
      } else {
        try {
          const res = await axios.delete(`${URL}/category`, {
            data: {
              serverId: serverId,
              userId: userId,
              categoryId: categoryId
            },
          });
          if (res.status === 200) {
            alert("삭제되었습니다");
            // 삭제가 성공하면 해당 categoryId와 일치하는 카테고리를 제거
            const updatedCategories = serverData.serverCategories.filter(category => category.categoryId !== categoryId);
            setServerData({ ...serverData, serverCategories: updatedCategories });
          } else {
            alert("삭제하는 중에 오류가 발생했습니다");
            console.error(res);
          }
        } catch (error) {
          alert("삭제하는 중에 오류가 발생했습니다");
          console.error(error);
        }
      }
    }
  }

  return (
    <>
      <form className={style.formWrapper} onSubmit={handleSubmit}>
        <div className={style.topContainer}>
          <h3><b> 카테고리 설정 </b></h3>
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
        
        <div className={style.createContainer} >
          <button className={style.createBtn} 
            style={{ backgroundColor: 'var(--main-purple)' }}
            type="submit">
            저장하기
          </button>
        </div>
        
        <button onClick={handleDelete}>
          삭제하기
        </button>
      </form>
    </>
  );
}

export default CategorySetting;
