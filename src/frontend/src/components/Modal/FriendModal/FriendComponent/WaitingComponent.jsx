import { useState } from "react";
import s from "./WaitingComponent.module.css";
import useFriendStore from "../../../../actions/useFriendStore";

function WaitingComponent({ friend }) {
  const [loading, setLoading] = useState(false);
  const { acceptFriendRequest, refuseFriendRequest } = useFriendStore();

  const handleAcceptClick = async () => {
    try {
      setLoading(true);
      console.log("요청 수락할 친구 Id :", friend.friendshipId);
      await acceptFriendRequest(friend.friendshipId);
    } catch (error) {
      console.error("친구 요청 수락 오류:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleRejectClick = async () => {
    try {
      setLoading(true);
      console.log("거절한 친구 id:", friend.friendshipId);
      await refuseFriendRequest(friend.friendshipId);
    } catch (error) {
      console.error("친구 요청 거절 오류:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <li key={friend.friendId} className={s.friendContainer}>
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
        <h4 className={s.friendName}>{friend.friendEmail}</h4>
      </div>
      <div className={s.friendFunction}>
        <button
          onClick={handleAcceptClick}
          className={s.acceptBtn}
          disabled={loading}
        >
          {loading ? "처리 중..." : "수락"}
        </button>
        <button
          onClick={handleRejectClick}
          className={s.refuseBtn}
          disabled={loading}
        >
          {loading ? "처리 중..." : "거절"}
        </button>
      </div>
    </li>
  );
}

export default WaitingComponent;
