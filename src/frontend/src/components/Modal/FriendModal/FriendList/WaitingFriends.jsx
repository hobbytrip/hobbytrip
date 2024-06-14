import { useState } from "react";
import { axiosInstance } from "../../../../utils/axiosInstance";
import API from "../../../../utils/API/API";
import useUserStore from "../../../../actions/useUserStore";
import s from "./FriendList.module.css";
import friendEmpty from "../../../../assets/image/friendEmpty.jpg";
import WaitingComponent from "../FriendComponent/WaitingComponent";

function FriendList({ friends }) {
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

  return (
    <div className={s.wrapper}>
      {!friends || friends.length === 0 ? (
        <div className={s.container}>
          <img src={friendEmpty} alt="친구없음" style={{ width: "150px" }} />
          <h4 className={s.h4}>아직 받은 친구 요청이 없어요</h4>
        </div>
      ) : (
        friends &&
        friends.map((friend) => (
          <WaitingComponent key={friend.friendshipId} friend={friend} />
        ))
      )}
    </div>
  );
}

export default FriendList;
