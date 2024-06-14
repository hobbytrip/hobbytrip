import { useState } from "react";
import style from "./MyFriend.module.css";
import { IoSearchOutline } from "react-icons/io5";
import FriendList from "../FriendList/FriendList";
import AddFriend from "../AddFriend/AddFriend";

const FriendMenu = ({ onAddFriendClick }) => {
  return (
    <div className={style.friendMenuContainer}>
      <h3> 내 친구</h3>
      <ul className={style.friendMenuList}>
        <li>
          <button>
            <h4>모두</h4>
          </button>
        </li>
        <li>
          <button>
            <h4>온라인</h4>
          </button>
        </li>
        <li>
          <button>
            <h4>대기 중</h4>
          </button>
        </li>
        <li>
          <button className={style.addFriend} onClick={onAddFriendClick}>
            <h4>친구 추가하기</h4>
          </button>
        </li>
      </ul>
    </div>
  );
};

const FriendSearch = () => {
  return (
    <div className={style.searchContainer}>
      <form className={style.searchForm}>
        <input
          type="text"
          placeholder="친구 검색하기"
          className={style.searchInput}
        />
        <button type="submit" className={style.searchBtn}>
          <IoSearchOutline
            style={{ width: "12.3px", height: "12px", color: "#434343" }}
          />
        </button>
      </form>
    </div>
  );
};

function MyFriend() {
  const [showAddFriend, setShowAddFriend] = useState(false);
  const handleAddFriendClick = () => {
    setShowAddFriend(true);
  };

  const handleBackToFriendsList = () => {
    setShowAddFriend(false);
  };

  return (
    <div className={style.wrapper}>
      {showAddFriend ? (
        <AddFriend onBackClick={handleBackToFriendsList} />
      ) : (
        <>
          <FriendMenu onAddFriendClick={handleAddFriendClick} />
          <FriendSearch />
          <div className={style.friendListContainer}>
            <FriendList />
          </div>
        </>
      )}
    </div>
  );
}

export default MyFriend;
