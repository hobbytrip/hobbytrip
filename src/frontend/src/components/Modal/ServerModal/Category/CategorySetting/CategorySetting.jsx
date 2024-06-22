import React, { useState, useEffect } from "react";
import style from "./CategorySetting.module.css";
import useServerStore from "../../../../../actions/useServerStore";
import API from "../../../../../utils/API/API";
import { axiosInstance } from "../../../../../utils/axiosInstance";
import useUserStore from "../../../../../actions/useUserStore";
import { HiHome } from "react-icons/hi2";

function CategorySetting({ categoryId, onClose }) {
  const [name, setName] = useState("");
  const { serverData, setServerCategories } = useServerStore((state) => ({
    serverData: state.serverData,
    setServerCategories: state.setServerCategories
  }));
  const { USER } = useUserStore();
  const userId = USER.userId;
  const serverId = serverData?.serverInfo?.serverId;

  useEffect(() => {
    if (serverData?.serverCategories) {
      const category = serverData.serverCategories.find(
        (cat) => cat.categoryId === categoryId
      );
      if (category) {
        setName(category.name);
      } else {
        console.error("Category not found.");
      }
    }
  }, [categoryId, serverData]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (name === "") {
      alert("카테고리 이름을 입력해주세요");
      return;
    }
    if (String(userId) !== String(serverData?.serverInfo?.managerId)) {
      alert("수정 권한이 없습니다");
      return;
    }
    try {
      const data = {
        userId,
        serverId,
        categoryId,
        name,
      };

      console.log("Sending data to API:", data); // Debug log

      const res = await axiosInstance.patch(API.COMM_CATEGORY, data);

      console.log("API Response:", res.data); // Debug log

      if (res.data.success) {
        const updatedCategory = res.data.data;
        const updatedCategories = serverData.serverCategories.map((category) => {
          if (category.categoryId === updatedCategory.categoryId) {
            return updatedCategory;
          }
          return category;
        });

        console.log("Updated Categories:", updatedCategories); // Debug log

        setServerCategories(updatedCategories);

        onClose();
      } else {
        console.log("카테고리 수정 실패.");
      }
    } catch (error) {
      console.error("데이터 patch 에러:", error);
    }
  };

  const handleDelete = async () => {
    const confirmDelete = window.confirm("정말 삭제하시겠습니까?");
    if (confirmDelete) {
      if (String(userId) !== String(serverData?.serverInfo?.managerId)) {
        alert("삭제 권한이 없습니다");
        return;
      }
      try {
        const res = await axiosInstance.delete(API.COMM_CATEGORY, {
          data: {
            serverId,
            userId,
            categoryId,
          },
        });

        console.log("Delete Response:", res); // Debug log

        if (res.status === 200) {
          alert("삭제되었습니다");
          const updatedCategories = serverData.serverCategories.filter(
            (category) => category.categoryId !== categoryId
          );

          setServerCategories(updatedCategories);
          onClose();
        } else {
          alert("삭제하는 중에 오류가 발생했습니다");
          console.error(res);
        }
      } catch (error) {
        alert("삭제하는 중에 오류가 발생했습니다");
        console.error(error);
      }
    }
  };

  return (
    <>
      <form className={style.formWrapper} onSubmit={handleSubmit}>
        <div className={style.topContainer}>
          <h3>
            <b>
              <HiHome style={{ marginRight: "5px" }} />
              카테고리 설정
            </b>
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
          <button
            className={style.createBtn}
            style={{ backgroundColor: "var(--main-purple)", color: "white" }}
            type="submit"
          >
            저장하기
          </button>
        </div>

        <button onClick={handleDelete}>
          <h5>삭제하기</h5>
        </button>
      </form>
      <button
        className={style.backBtn}
        onClick={onClose}
        style={{ color: "white" }}
      >
        <h4> 뒤로 가기</h4>
      </button>
    </>
  );
}

export default CategorySetting;
