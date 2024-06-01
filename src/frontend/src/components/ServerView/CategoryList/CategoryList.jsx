import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import style from './CategoryList.module.css';
import CreateItem from './CreateItem/CreateItem'
import Channel from './Channel/Channel';
import CreateChannel from '../../Modal/ServerModal/Channel/CreateChannel/CreateChannel';
import useServerStore from '../../../actions/useServerStore';
import { HiPlus } from "react-icons/hi2";
import { IoClose, IoSettings } from "react-icons/io5";

const userId = 1; // 테스트용

const CategoryList = () => {
  const [categories, setCategories] = useState([]);
  const [channels, setChannels] = useState([]);
  const [showCreateItemModal, setShowCreateItemModal] = useState(false);

  const nav = useNavigate();
  const { serverData } = useServerStore((state) => ({
    serverData: state.serverData,
  }));

  useEffect(() => {
    setCategories(serverData.serverCategories || []);
    setChannels(serverData.serverChannels || []);
  }, [serverData]);

  const handleCloseCategory = () => {
    nav('initialChat?');
  };

  const handleCloseCreateItemModal = () => {
    setShowCreateItemModal(false);
  };

  const handleAddItem = () => {
    setShowCreateItemModal(true);
  };

  const getCategoryChannels = (categoryId) => {
    return channels.filter(channel => channel.categoryId === categoryId);
  };

  return (
    <>
      <div className={style.categoryList}>
        <div className={style.categoryHeader}>
          <button onClick={handleAddItem}>
            <HiPlus style={{ width: '18px', height: '18px' }} />
          </button>
          <button onClick={handleCloseCategory}>
            <IoClose style={{ width: '18px', height: '18px' }} />
          </button>
        </div>
        {categories.map(category => (
          <Category
            key={category.categoryId}
            categoryId={category.categoryId}
            name={category.name}
            serverId={serverData.serverInfo.serverId}
            channels={getCategoryChannels(category.categoryId)}
          />
        ))}
      </div>
      {showCreateItemModal && (
        <div className={style.createServerModal}>
          <CreateItem userId={userId} onClose={handleCloseCreateItemModal} />
        </div>
      )}
    </>
  );
};

const Category = ({ categoryId, name, serverId, channels }) => {
  const [showCreateChannel, setShowCreateChannel] = useState(false);
  const [showCategorySetting, setShowCategorySetting] = useState(false);
  const nav = useNavigate();

  const handleAddChannel = () => {
    setShowCreateChannel(true);
  };

  const handleCloseCreateChannel = () => {
    setShowCreateChannel(false);
  };

  const handleCategorySetting = () => {
    setShowCategorySetting(true);
  };

  const handleCloseCategorySetting = () => {
    setShowCategorySetting(false);
  };

  return (
    <>
      <div className={style.category}>
        <div className={style.categoryName}>
          <h3>{name}</h3>
          <button className={style.iconPurple}>
            <IoSettings style={{ width: '17px', height: '17px' }} onClick={handleCategorySetting} />
          </button>
          <button className={style.iconPurple}>
            <HiPlus style={{ width: '17px', height: '17px' }} onClick={handleAddChannel} />
          </button>
        </div>
        <div className={style.categoryChannels}>
          {channels.map(channel => (
            <li key={channel.channelId}>
              <Channel channel={channel} serverId={serverId} />
            </li>
          ))}
        </div>
      </div>
      {showCreateChannel && (
        <div className={style.createServerModal}>
          <CreateChannel
            userId={userId}
            categoryId={categoryId}
            onClose={handleCloseCreateChannel}
          />
        </div>
      )}
      {showCategorySetting && (
        <div className={style.createServerModal}>
          <CategorySetting
            userId={userId}
            categoryId={categoryId}
            onClose={handleCloseCategorySetting}
          />
        </div>
      )}
    </>
  );
};

export default CategoryList;
