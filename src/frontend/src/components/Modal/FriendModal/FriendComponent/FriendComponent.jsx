import s from "./FriendComponent.module.css";
import { AiFillMessage } from "react-icons/ai";
import { VscKebabVertical } from "react-icons/vsc";

function FriendComponent({ friend }) {
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
        <h4 className={s.friendName}>{friend.friendName}</h4>
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
  );
}
export default FriendComponent;
