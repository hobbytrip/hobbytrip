import React from "react";
import s from "./AddFriend.module.css";

const AddFriend = ({ onBackClick }) => {
  return (
    <div className={s.wrapper}>
      <div className={s.des}>
        <h3 className={s.h3}>이메일로 친구를 추가할 수 있어요</h3>
        <h4 className={s.h4}>함께 여행할 친구를 찾아보세요.</h4>
      </div>

      <div className={s.container}>
        <form className={s.form}>
          <input
            type="email"
            placeholder="친구의 이메일을 입력해요!"
            className={s.input}
          />
          <button type="submit" className={s.addFriendBtn}>
            친구 추가
          </button>
        </form>
        <button onClick={onBackClick} className={s.backBtn}>
          돌아가기
        </button>
      </div>
    </div>
  );
};

export default AddFriend;
