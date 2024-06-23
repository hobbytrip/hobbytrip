import React, { useEffect, useState, useRef } from "react";
import { useNavigate, useParams } from "react-router-dom";
import style from "./CategoryList.module.css";
import CreateItem from "./CreateItem/CreateItem";
import Channel from "./Channel/Channel";
import CreateChannel from "../../../Modal/ServerModal/Channel/CreateChannel/CreateChannel";
import CategorySetting from "../../../Modal/ServerModal/Category/CategorySetting/CategorySetting";
import useUserStore from "../../../../actions/useUserStore";
import useServerStore from "../../../../actions/useServerStore";
import { HiPlus } from "react-icons/hi2";
import { IoClose, IoSettings } from "react-icons/io5";
import { axiosInstance } from "../../../../utils/axiosInstance";
import API from "../../../../utils/API/API";

const CategoryList = () => {
  const [categories, setCategories] = useState([]);
  const [channels, setChannels] = useState([]);
  const [uncategorizedChannels, setUncategorizedChannels] = useState([]);
  const [showCreateItemModal, setShowCreateItemModal] = useState(false);
  const nav = useNavigate();
  const { USER } = useUserStore();
  const userId = USER.userId;
  const { serverId } = useParams();

  const { serverData } = useServerStore();

  useEffect(() => {
    deleteNotice();
  }, [serverId]);

  useEffect(() => {
    setCategories(serverData.serverCategories || []);
    setChannels(serverData.serverChannels || []);
    setUncategorizedChannels(
      (serverData.serverChannels || []).filter((channel) => !channel.categoryId)
    );
  }, [serverData]);

  const deleteNotice = async () => {
    try {
      const res = await axiosInstance.delete(
        API.SERVER_SSE_DEL(serverId, userId),
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );
      console.log("Server notice reeeeeeeeeeeeead");
      console.log(res.data);
    } catch (error) {
      console.error("Error deleting notice:", error);
    }
  };

  const handleCloseCategory = () => {
    nav("initialChat?");
  };

  const handleCloseCreateItemModal = () => {
    setShowCreateItemModal(false);
  };

  const handleAddItem = () => {
    setShowCreateItemModal(true);
  };

  const getCategoryChannels = (categoryId) => {
    return channels.filter((channel) => channel.categoryId === categoryId);
  };

  return (
    <>
      <div className={style.categoryList}>
        <div className={style.categoryHeader}>
          <button onClick={handleAddItem}>
            <HiPlus
              style={{ width: "18px", height: "18px", marginBottom: "-30px" }}
            />
          </button>
          <button onClick={handleCloseCategory} className={style.closeCateBtn}>
            <IoClose style={{ width: "18px", height: "18px" }} />
          </button>
        </div>
        <div className={style.uncategorizedChannels}>
          {uncategorizedChannels.map((channel) => (
            <li key={channel.channelId}>
              <Channel channel={channel} serverId={serverId} />
            </li>
          ))}
        </div>
        {categories.map((category) => (
          <Category
            key={category.categoryId}
            categoryId={category.categoryId}
            name={category.name}
            serverId={serverId}
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
              style={{ width: "20px", height: "20px", marginRight: "-5px" }}
              onClick={handleCategorySetting}
            />
          </button>
          <button className={style.iconPurple}>
            <HiPlus
              style={{ width: "20px", height: "20px", marginRight: "-1px" }}
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
