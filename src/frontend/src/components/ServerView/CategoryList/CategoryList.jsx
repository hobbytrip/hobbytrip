import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import style from './CategoryList.module.css';
import { HiHome, HiPlus } from "react-icons/hi2";
import { TiUserAdd } from "react-icons/ti";
import { IoVideocam, IoSettings, IoDocument, IoClose } from "react-icons/io5";
import CreateChannel from '../../Modal/ServerModal/Channel/CreateChannel/CreateChannel';
import CreateCategory from '../../Modal/ServerModal/Category/CreateCategory/CreateCategory';
import useServerStore from '../../../actions/useServerStore';
import CategorySetting from '../../Modal/ServerModal/Category/CategorySetting/CategorySetting';

const URL = 'http://localhost:8080';

const userId = 1; // 테스트용

const CategoryList = () => {
  const [categories, setCategories] = useState([]);
  const [channels, setChannels] = useState([]);
  const [showCreateCategory, setShowCreateCategory] = useState(false);
  
  const nav = useNavigate();
  
  const { serverData } = useServerStore((state) => ({
    serverData: state.serverData,
  }));
  
  useEffect(() => {
    setCategories(serverData.serverCategories || []);
    setChannels(serverData.serverChannels || []);
  }, [serverData]);

  const serverId = serverData.serverInfo.serverId;
  
  const handleCloseCategory = () => {
    nav('initialChat?');
  }

  const handleCloseCreateChannel = () => {
    setShowCreateCategory(false);
  }

  const handleAddCategory = () => {
    setShowCreateCategory(true);
  }

  return (
    <>
    <div className={style.categoryList}>
      <div className={style.categoryHeader}>
        <button onClick={handleAddCategory}>
          <HiPlus style={{width: '18px', height: '18px'}} />
        </button>
        <button onClick={handleCloseCategory}>
          <IoClose style={{width: '18px', height: '18px'}} />
        </button>
      </div>
      {categories.map(category => ( 
        <Category key={category.categoryId} categoryId={category.categoryId} name={category.name}>
          {channels
            .filter(channel => channel.categoryId === category.categoryId)
            .map(channel => (
              <li key={channel.channelId}>
                {channel.channelType === 'CHAT' ? (
                  <ChatChannel channel={channel} />
                ) : channel.channelType === 'VOICE' ? (
                  <VoiceChannel channel={channel} />
                ) : (
                  <ForumChannel channel={channel} />
                )}
              </li>
            ))}
        </Category>
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


const Category = ({ categoryId, name, channels }) => {
  const [showCreateChannel, setShowCreateChannel] = useState(false);
  const [showCategorySetting, setShowCategorySetting] = useState(false);

  const handleAddChannel = () => {
    setShowCreateChannel(true);
  }

  const handleCloseCreateChannel = () => {
    setShowCreateChannel(false);
  };
  
  const handleCategorySetting = () => {
    setShowCategorySetting(true);
  }


  const handleCloseCategorySetting = () => {
    setShowCategorySetting(false);
  };

  return (
    <>
      <div className={style.category}>
        <div className={style.categoryName}>
          <h3>{name}</h3>
          <button>
            <IoSettings style={{ width: '16px', height: '16px' }} onClick={handleCategorySetting}/>
          </button>
          <button>
            <HiPlus style={{ width: '16px', height: '16px' }} onClick={handleAddChannel} />
          </button>
        </div>
        <div className={style.categoryChannels}>
          {channels}
        </div>
      </div>
      {/* ! 채널 만들기 ! */}
      {showCreateChannel && (
        <div className={style.createServerModal}>
          <CreateChannel />
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
          <button className={style.closeBtn} onClick={handleCloseCategorySetting}>
            <h4 style={{ color: "#fff", textDecoration: "underline" }}>
              뒤로 가기
            </h4>
          </button>
        </div>
      )}
    </>
  );
};

const ChatChannel = ({ channel }) => {
  return (
    <div className={style.channel}>
      <HiHome style={{ width: '20px', height: '20px' }} />
      <div className={style.channelName}>
        {/* <h3>{channel}</h3> */}
      </div>
      <TiUserAdd style={{ width: '16px', height: '16px' }} />
      <IoSettings style={{ width: '17px', height: '17px' }} />
    </div>
  );
};

const VoiceChannel = ({ channel }) => {
  return (
    <div className={style.channel}>
      <IoVideocam style={{ width: '20px', height: '20px' }} />
      <div className={style.channelName}>
        {/* <h3>{channel.name}</h3> */}
      </div>
      <TiUserAdd style={{ width: '16px', height: '16px' }} />
      <IoSettings style={{ width: '17px', height: '17px' }} />
    </div>
  );
};

const ForumChannel = ({ channel }) => {
  return (
    <div className={style.channel}>
      <IoDocument style={{ width: '20px', height: '20px' }} />
      <div className={style.channelName}>
        {/* <h3>{channel.name}</h3> */}
      </div>
      <TiUserAdd style={{ width: '16px', height: '16px' }} />
      <IoSettings style={{ width: '17px', height: '17px' }} />
    </div>
  );
};

export default CategoryList;
