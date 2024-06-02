import style from './Channel.module.css';
import useServerStore from '../../../../actions/useServerStore';
import ChannelSetting from '../../../Modal/ServerModal/Channel/ChannelSetting/ChannelSetting';
import { HiHome } from "react-icons/hi2";
import { IoSettings, IoDocument, IoVideocam } from "react-icons/io5";
import { TiUserAdd } from "react-icons/ti";
import { useNavigate } from "react-router-dom";
import { useState } from 'react';
import useUserStore from '../../../../actions/useUserStore';

const Channel = ({ channel, serverId }) => {
  const [showChannelSetting, setShowChannelSetting] = useState(false);

  const nav = useNavigate();
  const { userId } = useUserStore();
  const channelId = channel.channelId;
  const type = channel.channelType;

  const getIcon = (type) => {
    switch (type) {
      case "CHAT":
        return <HiHome style={{ width: '20px', height: '20px' }} />;
      case "VOICE":
        return <IoVideocam style={{ width: '20px', height: '20px' }} />;
      case "FORUM":
        return <IoDocument style={{ width: '20px', height: '20px' }} />;
      default:
        return null;
    }
  };

  const handleChannelClick = () => {
    const url = `/${serverId}/${channelId}/`
    switch (type) {
      case "CHAT":
        nav(url + 'chat');
        break;
      case "VOICE":
        nav(url + 'vid');
        break;  
      case "FORUM":
        nav(url + 'forum');
        break;
      default:
        return null;
    }
  }

  const handleChannelSetting = () => {
    setShowChannelSetting(true);
  };

  const handleClose = () => {
    setShowChannelSetting(false);
  };

  return (
    <>
      <div className={style.channelContainer}>
        <button onClick={handleChannelClick} className={style.name}>
          {getIcon(type)}
          <h3>{channel.name}</h3>
        </button>
        <TiUserAdd style={{ width: '17px', height: '17px' }} />
        <IoSettings 
          style={{ width: '17px', height: '17px' }}
          onClick={handleChannelSetting} />
      </div>
      {showChannelSetting && (
        <div className={style.createServerModal}>
          <ChannelSetting 
            userId={userId}
            channel={channel}
            onClose={handleClose}
          />
        </div>
      )}
    </>
  );
};

export default Channel;
