import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import style from "./CategoryList.module.css";
import CreateItem from "./CreateItem/CreateItem";
import Channel from "./Channel/Channel";
import CreateChannel from "../../Modal/ServerModal/Channel/CreateChannel/CreateChannel";
import CategorySetting from "../../Modal/ServerModal/Category/CategorySetting/CategorySetting";
import useUserStore from "../../../actions/useUserStore";
import useServerStore from "../../../actions/useServerStore";
import { HiPlus } from "react-icons/hi2";
import { IoClose, IoSettings } from "react-icons/io5";

const CategoryList = () => {
  const [showCreateItemModal, setShowCreateItemModal] = useState(false);
  const { USER } = useUserStore();
  const userId = USER.userId;
  const nav = useNavigate();
  const { serverData, fetchServerData } = useServerStore((state) => ({
    serverData: state.serverData,
    fetchServerData: state.fetchServerData,
  }));
  const serverId = serverData?.serverInfo?.serverId;

  useEffect(() => {
    if (serverId && userId) {
      fetchServerData(serverId, userId);
    }
  }, [serverData]);

  const handleCloseCategory = () => {
    nav(-1);
  };

  const handleCloseCreateItemModal = () => {
    setShowCreateItemModal(false);
  };

  const handleAddItem = () => {
    setShowCreateItemModal(true);
  };

  const getCategoryChannels = (categoryId) => {
    return (serverData?.serverChannels || []).filter((channel) => channel.categoryId === categoryId);
  };

  return (
    <>
      <div className={style.categoryList}>
        <div className={style.categoryHeader}>
          <button onClick={handleAddItem}>
            <HiPlus style={{ width: "18px", height: "18px" }} />
          </button>
          <button onClick={handleCloseCategory}>
            <IoClose style={{ width: "18px", height: "18px" }} />
          </button>
        </div>
        <div className={style.uncategorizedChannels}>
          {(serverData?.serverChannels || []).filter((channel) => !channel.categoryId).map((channel) => (
            <li key={channel.channelId}>
              <Channel channel={channel} serverId={serverData?.serverInfo?.serverId} />
            </li>
          ))}
        </div>
        {(serverData?.serverCategories || []).map((category) => (
          <Category
            key={category.categoryId}
            categoryId={category.categoryId}
            name={category.name}
            serverId={serverData?.serverInfo?.serverId}
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
            <IoSettings
              style={{ width: "17px", height: "17px" }}
              onClick={handleCategorySetting}
            />
          </button>
          <button className={style.iconPurple}>
            <HiPlus
              style={{ width: "17px", height: "17px" }}
              onClick={handleAddChannel}
            />
          </button>
        </div>
        <div className={style.categoryChannels}>
          {channels.map((channel) => (
            <li key={channel.channelId}>
              <Channel channel={channel} serverId={serverId} />
            </li>
          ))}
        </div>
      </div>
      {showCreateChannel && (
        <div className={style.createServerModal}>
          <CreateChannel
            categoryId={categoryId}
            onClose={handleCloseCreateChannel}
          />
        </div>
      )}
      {showCategorySetting && (
        <div className={style.createServerModal}>
          <CategorySetting
            categoryId={categoryId}
            onClose={handleCloseCategorySetting}
          />
        </div>
      )}
    </>
  );
};

export default CategoryList;
