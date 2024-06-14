import { useEffect, useState } from "react";
import { axiosInstance } from "../../../../utils/axiosInstance";
import API from "../../../../utils/API/API";
import useUserStore from "../../../../actions/useUserStore";
import useFriendStore from "../../../../actions/useFriendStore";
import s from "./FriendList.module.css";
import FriendComponent from "../FriendComponent/FriendComponent";

function FriendList() {
  const [dmNotice, setDmNotice] = useState([]);
  const { userId } = useUserStore();
  const { friends, fetchFriends } = useFriendStore();

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
    fetchFriends();
    getNotice();
  }, [fetchFriends]);

  return (
    <div className={s.wrapper}>
      {!friends ? (
        <div>친구 없음.</div>
      ) : (
        friends &&
        friends.map((friend) => (
          <FriendComponent key={friend.friendId} friend={friend} />
        ))
      )}
    </div>
  );
}

export default FriendList;
