import style from "./MyFriend.module.css";
import { IoSearchOutline } from "react-icons/io5";
import FriendComponent from "../FriendComponent/FriendComponent";

const FriendMenu = () => {
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
          <button className={style.addFriend}>
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

const MyFriend = () => {
  return (
    <div className={style.wrapper}>
      <FriendMenu />
      <FriendSearch />
      <div className={style.friendListContainer}>
        <FriendComponent />
      </div>
    </div>
  );
};

export default MyFriend;
