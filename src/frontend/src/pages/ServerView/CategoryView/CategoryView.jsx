import style from "./CategoryView.module.css";
import CategoryList from "../../../components/ServerView/CategoryList/CategoryList";
import ChatHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatHeaderModal from "../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo";
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar";

const CategoryView = () => {
  return (
    <>
      <div className={style.wrapper}>
        <div className={style.container}>
          <div className={style.topContainer}>
            <ChatHeader />
            <ChatHeaderModal />
            <ChatSearchBar />
          </div>
          <CategoryList />
        </div>
      </div>
    </>
  );
};

export default CategoryView;
