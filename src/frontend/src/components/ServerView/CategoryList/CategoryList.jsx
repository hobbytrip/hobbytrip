import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import style from "./CategoryList.module.css";
import CreateCategory from "../../Modal/ServerModal/Category/CreateCategory/CreateCategory";
import CategorySetting from "../../Modal/ServerModal/Category/CategorySetting/CategorySetting";
import CreateChannel from "../../Modal/ServerModal/Channel/CreateChannel/CreateChannel";
import Channel from "./Channel/Channel";
import useServerStore from "../../../actions/useServerStore";
import { HiPlus } from "react-icons/hi2";
import { IoSettings, IoClose } from "react-icons/io5";
import useUserStore from "../../../actions/useUserStore";

const CategoryList = () => {
  const [categories, setCategories] = useState([]);
  const [channels, setChannels] = useState([]);
  const [showCreateCategory, setShowCreateCategory] = useState(false);
  const { userId } = useUserStore();

  const nav = useNavigate();
  const { serverData } = useServerStore((state) => ({
    serverData: state.serverData,
  }));

  useEffect(() => {
    setCategories(serverData.serverCategories || []);
    setChannels(serverData.serverChannels || []);
  }, [serverData]);

  const handleCloseCategory = () => {
    nav("initialChat?");
  };

  const handleCloseCreateChannel = () => {
    setShowCreateCategory(false);
  };

  const handleAddCategory = () => {
    setShowCreateCategory(true);
  };

  const getCategoryChannels = (categoryId) => {
    return channels.filter((channel) => channel.categoryId === categoryId);
  };

  return (
    <>
      <div className={style.categoryList}>
        <div className={style.categoryHeader}>
          <button onClick={handleAddCategory}>
            <HiPlus style={{ width: "18px", height: "18px" }} />
          </button>
          <button onClick={handleCloseCategory}>
            <IoClose style={{ width: "18px", height: "18px" }} />
          </button>
        </div>
        {categories.map((category) => (
          <Category
            key={category.categoryId}
            categoryId={category.categoryId}
            name={category.name}
            serverId={serverData.serverInfo.serverId}
            channels={getCategoryChannels(category.categoryId)}
          />
        ))}
      </div>
      {/* ! 카테고리 생성하기 ! */}
      {showCreateCategory && (
        <div className={style.createServerModal}>
          <CreateCategory userId={userId} onClose={handleCloseCreateChannel} />
          <button className={style.closeBtn} onClick={handleCloseCreateChannel}>
            <h4 style={{ color: "#fff", textDecoration: "underline" }}>
              뒤로 가기
            </h4>
          </button>
        </div>
      )}
    </>
  );
};

const Category = ({ categoryId, name, serverId, channels }) => {
  const [showCreateChannel, setShowCreateChannel] = useState(false);
  const [showCategorySetting, setShowCategorySetting] = useState(false);
  const nav = useNavigate();
  const { userId } = useUserStore();

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
      {/* ! 채널 만들기 ! */}
      {showCreateChannel && (
        <div className={style.createServerModal}>
          <CreateChannel
            userId={userId}
            categoryId={categoryId}
            onClose={handleCloseCreateChannel}
          />
          <button className={style.closeBtn} onClick={handleCloseCreateChannel}>
            <h4 style={{ color: "#fff", textDecoration: "underline" }}>
              뒤로 가기
            </h4>
          </button>
        </div>
      )}
      {/* ! 카테고리 수정 삭제 ! */}
      {showCategorySetting && (
        <div className={style.createServerModal}>
          <CategorySetting
            userId={userId}
            categoryId={categoryId}
            onClose={handleCloseCategorySetting}
          />
          <button
            className={style.closeBtn}
            onClick={handleCloseCategorySetting}
          >
            <h4 style={{ color: "#fff", textDecoration: "underline" }}>
              뒤로 가기
            </h4>
          </button>
        </div>
      )}
    </>
  );
};

export default CategoryList;
