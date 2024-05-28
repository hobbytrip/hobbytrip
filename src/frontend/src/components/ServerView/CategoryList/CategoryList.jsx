import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import style from './CategoryList.module.css';
import { FiPlus } from "react-icons/fi";
import { HiHome } from "react-icons/hi2";
import { TiUserAdd } from "react-icons/ti";
import { IoVideocam, IoSettings, IoDocument } from "react-icons/io5";
import CreateChannel from '../../Modal/ServerModal/CreateChannel.jsx/CreateChannel';

const URL = 'http://localhost:8080';

const CategoryList = () => {
  
  return (
    <div className={style.categoryList}>
      <div className={style.categoryHeader}>
        <button>
          {/* 카테고리 추가 */}
        </button>
        <button>
          {/* 나가기 */}
        </button>
      </div>
      
      {/* {categories.map(category => (
        <Category key={category.categoryId} name={category.name}>
          {channels
            .filter(channel => channel.categoryId === category.categoryId)
            .map(channel => (
              <li key={channel.channelId}>
                {channel.channelType === 'CHAT' ? (
                  <ChatChannel name={channel.name} />
                ) : channel.channelType === 'VOICE' ? (
                  <VoiceChannel name={channel.name} />
                ) : (
                  <ForumChannel name={channel.name} />
                )}
              </li>
            ))}
        </Category>
      ))} */}
    </div>
  );
};

const ChatChannel = ({ name }) => {
  return (
    <div className={style.channel}>
      <HiHome style={{ width: '20px', height: '20px' }} />
      <div className={style.channelName}>
        <h3>{name}</h3>
      </div>
      <TiUserAdd style={{ width: '16px', height: '16px' }} />
      <IoSettings style={{ width: '17px', height: '17px' }} />
    </div>
  );
};

const VoiceChannel = ({ name }) => {
  return (
    <div className={style.channel}>
      <IoVideocam style={{ width: '20px', height: '20px' }} />
      <div className={style.channelName}>
        <h3>{name}</h3>
      </div>
      <TiUserAdd style={{ width: '16px', height: '16px' }} />
      <IoSettings style={{ width: '17px', height: '17px' }} />
    </div>
  );
};

const ForumChannel = ({ name }) => {
  return (
    <div className={style.channel}>
      <IoDocument style={{ width: '20px', height: '20px' }} />
      <div className={style.channelName}>
        <h3>{name}</h3>
      </div>
      <TiUserAdd style={{ width: '16px', height: '16px' }} />
      <IoSettings style={{ width: '17px', height: '17px' }} />
    </div>
  );
};

const Category = ({ name, children }) => {
  const [showCreateChannel, setShowCreateChannel] = useState(false);

  const handleCloseModal = () => {
    setShowCreateChannel(false);
  };

  const handleAddChannel = () => {
    setShowCreateChannel(true);
  }
  return (
    <>
    <div className={style.category}>
      <div className={style.categoryHeader}>
        <h3>{name}</h3>
        <button>
          <FiPlus style={{ width: '12px', height: '12px' }}
            onClick={handleAddChannel} />
        </button>
      </div>
      <div className={style.categoryChannels}>
        {children}
      </div>
    </div>
    {showCreateChannel && (
      <div className={style.createServerModal}>
        <CreateChannel />
        <button className={style.closeBtn} onClick={handleCloseModal}>
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
