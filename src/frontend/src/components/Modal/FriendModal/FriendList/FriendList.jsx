import { useEffect, useState } from "react";
import { axiosInstance } from "../../../../utils/axiosInstance";
import { useQuery } from "@tanstack/react-query";
import API from "../../../../utils/API/API";
import useUserStore from "../../../../actions/useUserStore";
import useFriendStore from "../../../../actions/useFriendStore";
import s from "./FriendList.module.css";
import { AiFillMessage } from "react-icons/ai";
import { VscKebabVertical } from "react-icons/vsc";

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
      {friends &&
        friends.map((friend) => (
          <li key={friend.id} className={s.friendContainer}>
            <div className={s.friendImg}>
              <img
                src={
                  friend.friendImageUrl ||
                  "./../../../../src/assets/image/default-logo.png"
                }
                alt="친구 이미지"
              />
            </div>
            <div className={s.friendData}>
              <h4 className={s.friendName}>{friend.friendName}</h4>
              <h4 className={s.friendEmail}>{friend.name}</h4>
              <h4 className>{friend.connectionState}</h4>
            </div>
            <div className={s.friendFunction}>
              <button>
                <AiFillMessage style={{ width: "15.62px", height: "15.6px" }} />
              </button>
              <button>
                <VscKebabVertical style={{ height: "15px" }} />
              </button>
            </div>
          </li>
        ))}
    </div>
  );
}

export default FriendList;
