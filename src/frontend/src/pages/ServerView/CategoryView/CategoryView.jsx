import style from './CategoryView.module.css';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import CategoryList from '../../../components/ServerView/CategoryList/CategoryList';
import ChatHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatHeaderModal from "../../../components/Modal/ChatModal/ChatHeaderModal/ChatHeaderModal";
import ChatSearchBar from "../../../components/Modal/ChatModal/ChatSearchBar/ChatSearchBar";
import useServerStore from '../../../actions/useServerStore';

const CategoryView = () => {
  const { serverId } = useParams();
  const userId = 1; // 테스트용
  const { serverData, fetchServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    fetchServerData: state.fetchServerData,
  }));

  useEffect(() => {
    fetchServerData(serverId, userId);
  }, []);

  const { serverInfo, serverCategories, serverChannels } = serverData;
  console.log(serverInfo)

  return (
    <>
      <div className={style.wrapper}>
        <div className={style.container}>
          <div className={style.topContainer}>
            <ChatHeader />
            {serverInfo  && (
              <ChatHeaderModal />
            )}
            <ChatSearchBar />
          </div>
          <CategoryList categories={serverCategories} channels={serverChannels}/>
        </div>
      </div>
    </>
  );
};

export default CategoryView;
