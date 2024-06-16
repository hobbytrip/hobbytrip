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

function App() {
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
