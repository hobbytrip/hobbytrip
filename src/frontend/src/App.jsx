import React from "react";
import axios from "axios";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import StartView from "./components/StartView/StartView";
import LoginView from "./pages/LoginView/Loginview";
import RegView from "./pages/RegView/RegView";
import UserProfileView from "./pages/UserProfileView/UserProfileView";

axios.defaults.withCredentials = true;

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<StartView />} />
        <Route path="/login" element={<LoginView />} />
        <Route path="/register" element={<RegView />} />
        <Route path="/user/profile" element={<UserProfileView />} />
      </Routes>
    </Router>
  );
}

export default App;