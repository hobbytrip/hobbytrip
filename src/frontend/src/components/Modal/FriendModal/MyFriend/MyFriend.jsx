import { useState, useEffect } from "react";
import style from "./MyFriend.module.css";
import { IoSearchOutline } from "react-icons/io5";
import FriendList from "../FriendList/FriendList";
import AddFriend from "../AddFriend/AddFriend";
import WaitingFriends from "../FriendList/WaitingFriends";
import useFriendStore from "../../../../actions/useFriendStore";

const FriendMenu = ({
  onAllFriendsClick,
  onOnlineFriendsClick,
  onWaitingFriendClick,
  onAddFriendClick,
}) => {
  return (
    <div
      className={style.friendMenuContainer}
      style={{ fontFamily: "DOSSaemmul" }}
    >
      <h3> 내 친구</h3>
      <ul className={style.friendMenuList}>
        <li>
          <button onClick={onAllFriendsClick}>
            <h4>모두</h4>
          </button>
        </li>
        <li>
          <button onClick={onOnlineFriendsClick}>
            <h4>온라인</h4>
          </button>
        </li>
        <li>
          <button
            className={style.waitingFriend}
            onClick={onWaitingFriendClick}
          >
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
  const [view, setView] = useState("all");
  const { fetchFriends, fetchWaitingFriends, friendList, waitingFriends } =
    useFriendStore();

  const handleAddFriendClick = () => {
    setShowAddFriend(true);
  };

  const handleBackToFriendsList = () => {
    setShowAddFriend(false);
  };

  const handleAllFriendsClick = () => {
    setView("all");
    fetchFriends();
  };

  const handleOnlineFriendsClick = () => {
    setView("online");
    fetchFriends();
  };

  const handleWaitingFriendClick = () => {
    setView("waiting");
    fetchWaitingFriends();
  };

  //친구목록 불러오기
  useEffect(() => {
    fetchFriends();
  }, [fetchFriends]);

  return (
    <div className={style.wrapper}>
      {showAddFriend ? (
        <AddFriend onBackClick={handleBackToFriendsList} />
      ) : (
        <>
          <FriendMenu
            onAllFriendsClick={handleAllFriendsClick}
            onOnlineFriendsClick={handleOnlineFriendsClick}
            onWaitingFriendClick={handleWaitingFriendClick}
            onAddFriendClick={handleAddFriendClick}
          />
          <FriendSearch />
          <div className={style.friendListContainer}>
            {view === "all" && <FriendList friends={friendList} />}
            <FriendList
              friends={friends.filter(
                (friend) => friend.connectionState === "ONLINE"
              )}
            />
            {view === "waiting" && <WaitingFriends friends={waitingFriends} />}
          </div>
        </>
      )}
    </div>
  );
}

export default MyFriend;
