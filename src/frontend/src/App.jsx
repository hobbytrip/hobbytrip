import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainView from "./pages/MainView/MainView.jsx";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/main" element={<MainView />} />
      </Routes>
    </Router>
  );
}

export default App;
