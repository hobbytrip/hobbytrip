import style from './CategoryView.module.css';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import CategoryList from '../../../components/ServerView/CategoryList/CategoryList';
import ChatHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatHeaderModal from '../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo';
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar";
import useUserStore from '../../../actions/useUserStore';
import useServerStore from '../../../actions/useServerStore';

const CategoryView = () => {
  const { serverData, fetchServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    fetchServerData: state.fetchServerData,
  }));

  const { serverId } = useParams();
  const { userId } = useUserStore();

  useEffect(() => {
    fetchServerData(userId, serverId);
  }, []);

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
