import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import StartView from "./components/StartView/StartView";
import LoginView from "./pages/LoginView/Loginview";
import RegView from "./pages/RegView/RegView";
import UserProfileView from "./pages/UserProfileView/UserProfileView";
import MediaView from "./pages/MediaView/MediaView";
import MenuView from "./pages/MenuView/MenuView.jsx";
import MainView from "./pages/MainView/MainView";
import ForumChatRoom from "./pages/Chat/ChatRoom/ForumChatRoom.jsx";
import ForumRoom from "./pages/Chat/ForumRoom/ForumRoom.jsx";
import SettingView from "./pages/ServerView/SettingView/SettingView.jsx";
import Server from "./components/TYPE/Community/Server.jsx";
import DM from "./components/TYPE/DM/DM.jsx";
import "./utils/notification/fcm.js";
import { getToken } from "firebase/messaging";
import { messaging } from "./utils/notification/fcm.js"

const { VITE_APP_VAPID_KEY } = import.meta.env;

function App() {
  React.useEffect(() => {
    async function initSw() {
      if ('serviceWorker' in navigator) {
        try {
          const registration = await navigator.serviceWorker.register('/firebase-messaging-sw.js');
          console.log('서비스 워커가 범위와 함께 등록되었습니다: ', registration.scope);
        } catch (err) {
          console.error('서비스 워커 등록에 실패했습니다: ', err);
        }
      }
    }

    async function requestPermission() {
      try {
        const permission = await Notification.requestPermission();
        if (permission === "granted") {
          const token = await getToken(messaging, {
            vapidKey: VITE_APP_VAPID_KEY,
          });
          console.log("Token generated : ", token);
        } else if (permission === "denied") {
          alert("You denied for the notification");
        }
      } catch (err) {
        console.error('Notification permission request failed: ', err);
      }
    }

    initSw();
    requestPermission();
  }, []);
  
  return (
    <Router>
      <Routes>
        <Route path="/" element={<StartView />} />
        <Route path="/login" element={<LoginView />} />
        <Route path="/register" element={<RegView />} />
        <Route path="/user/profile" element={<UserProfileView />} />
        <Route path="/:serverId/:channelId/chat" element={<Server />} />
        {/* <Route
          path=":serverId/:channelId/chat"
          element={<CommunityChatRoom />}
        /> */}
        <Route path=":serverId/:channelId/forum" element={<ForumRoom />} />
        <Route
          path=":serverId/:channelId/forum/:forumId/chat"
          element={<ForumChatRoom />}
        />
        <Route path="/:dmId/dm" element={<DM />} />
        <Route path="/user/profile" element={<UserProfileView />} />
        <Route path="/main" element={<MainView />} />
        <Route path="/menu" element={<MenuView />} />
        <Route path="/:serverId/setting" element={<SettingView />} />
        {/* <Route path="/:serverId/menu" element={<CategoryView />} />
        <Route path="/:serverId/friends" element={<FriendsView />} /> */}
        <Route path="/:serverId/:channelId/vid" element={<MediaView />} />
      </Routes>
    </Router>
  );
}

export default App;
