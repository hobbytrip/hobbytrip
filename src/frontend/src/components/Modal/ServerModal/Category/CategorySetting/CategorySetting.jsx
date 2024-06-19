import React, { useState, useEffect } from "react";
import style from "./CategorySetting.module.css";
import useServerStore from "../../../../../actions/useServerStore";
import API from "../../../../../utils/API/API";
import { axiosInstance } from "../../../../../utils/axiosInstance";
import useUserStore from "../../../../../actions/useUserStore";
import { HiHome } from "react-icons/hi2";

function CategorySetting({ categoryId, onClose }) {
  const [name, setName] = useState("");
  const { serverData, setServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    setServerData: state.setServerData,
  }));
  const { USER } = useUserStore();
  const userId = USER.userId;

  const serverId = serverData.serverInfo.serverId;
  useEffect(() => {
    const fetchCategory = async () => {
      try {
        const category = serverData.serverCategories.find(
          (cat) => cat.categoryId === categoryId
        );
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
    if (name === "") {
      alert("카테고리 이름을 입력해주세요");
      return;
    }
    if (String(userId) !== String(serverData.serverInfo.managerId)) {
      alert("수정 권한이 없습니다");
      return;
    }
    try {
      const data = {
        userId: userId,
        serverId: serverData.serverInfo.serverId,
        categoryId: categoryId,
        name: name,
      };

      const res = await axiosInstance.patch(API.COMM_CATEGORY, data);

      if (res.data.success) {
        console.log(res);
        const updatedCategory = res.data.data;
        const updatedCategories = serverData.serverCategories.map(
          (category) => {
            if (category.categoryId === updatedCategory.categoryId) {
              return updatedCategory;
            }
            return category;
          }
        );
        setServerData({ ...serverData, serverCategories: updatedCategories });
        onClose();
      } else {
        console.log("마을 수정 실패.");
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
          const res = await axiosInstance.delete(API.COMM_CATEGORY, {
            data: {
              serverId: serverId,
              userId: userId,
              categoryId: categoryId,
            },
          });
          if (res.status === 200) {
            alert("삭제되었습니다");
            const updatedCategories = serverData.serverCategories.filter(
              (category) => category.categoryId !== categoryId
            );
            setServerData({
              ...serverData,
              serverCategories: updatedCategories,
            });
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
    }
  };

  return (
    <>
      <form className={style.formWrapper} onSubmit={handleSubmit}>
        <div className={style.topContainer}>
          <h3>
            <b>
              <HiHome style={{ marginRight: "5px" }} />
              마을 설정
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
