import s from "./WaitingComponent.module.css";

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
        <button className={s.acceptBtn}>
          <h4>수락</h4>
        </button>
        <button className={s.refuseBtn}>
          <h4>거절</h4>
        </button>
      </div>
    </li>
  );
}
export default FriendComponent;
