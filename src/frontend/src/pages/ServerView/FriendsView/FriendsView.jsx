import style from './FriendsView.module.css';
import FriendsList from '../../../components/ServerView/FriendsList/FriendsList';
import ChatHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatHeaderModal from '../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo';
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar";

const FriendsView = () => {
  return (
    <>
      <div className={style.wrapper}>
        <div className={style.container}>
          <div className={style.topContainer}>
            <ChatHeader />
            <ChatHeaderModal />
            <ChatSearchBar />
          </div>
          <FriendsList />
        </div>
      </div>
    </>
  );
};

export default FriendsView;
