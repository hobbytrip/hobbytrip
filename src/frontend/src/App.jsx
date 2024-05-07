import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainView from "./pages/MainView/MainView.jsx";
import MenuView from "./pages/MenuView/MenuView.jsx";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/main" element={<MainView />} />
        <Route path="/menu" element={<MenuView />} />
      </Routes>
    </Router>
  );
}

export default App;
