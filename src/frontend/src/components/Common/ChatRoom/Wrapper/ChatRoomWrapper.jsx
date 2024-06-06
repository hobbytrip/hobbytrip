import React from "react";
import s from "./ChatRoomWrapper.module.css";
import TopHeader from "../CommunityChatHeader/ChatHeader";
import ChatRoomInfo from "../ChatRoomInfo/ChatRoomInfo";
import ChatSearchBar from "../ChatSearchBar/ChatSearchBar";

const ChatRoomWrapper = ({ children }) => {
  return (
    <div className={s.chatRoomWrapper}>
      <div className={s.wrapper}>
        <div className={s.topContainer}>
          <TopHeader />
          <ChatRoomInfo />
          <ChatSearchBar />
        </div>

        <div className={s.container}>{children}</div>
      </div>
    </div>
  );
};

export default ChatRoomWrapper;
