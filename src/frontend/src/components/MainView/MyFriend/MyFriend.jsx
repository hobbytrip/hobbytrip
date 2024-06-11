import { useEffect, useState } from "react";
import style from "./MyFriend.module.css";
import { axiosInstance } from "../../../utils/axiosInstance";
import API from "../../../utils/API/API";
import { IoSearchOutline } from "react-icons/io5";
import { AiFillMessage } from "react-icons/ai";
import { VscKebabVertical } from "react-icons/vsc";
import useUserStore from "../../../actions/useUserStore";

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

const FriendList = () => {
  const [dmNotice, setDmNotice] = useState([]);
  const { userId } = useUserStore();

  const getNotice = async () => {
    try {
      const res = await axiosInstance.get(API.DM_SSE_MAIN(userId));
      if (res.data.success) {
        setDmNotice(res.data.data);
        console.log(res.data.data);
      }
    } catch (error) {
      console.error("Error fetching server notices:", error);
    }
  };

  useEffect(() => {
    getNotice();
  }, []);
  return (
    <div className={style.friendListContainer}>
      <li className={style.friendContainer}>
        <div className={style.friendImg}>
          <img
            src="./../../../../src/assets/image/default-logo.png"
            alt="친구 이미지"
          />
        </div>
        <div className={style.friendData}>
          <h4 className={style.friendName}>친구 </h4>
          <h5 className={style.friendIntro}>친구 소개</h5>
        </div>
        <div className={style.friendFunction}>
          <button>
            <AiFillMessage style={{ width: "15.62px", height: "15.6px" }} />
          </button>
          <button>
            <VscKebabVertical style={{ height: "15px" }} />
          </button>
        </div>
      </li>
    </div>
  );
};

const MyFriend = () => {
  return (
    <div className={style.wrapper}>
      <FriendMenu />
      <FriendSearch />
      <FriendList />
    </div>
  );
};

export default MyFriend;
