import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
// import StartView from "./components/StartView/StartView";

function App() {
  return (
    <Router>
      <Routes>
        {/* <Route path="/" element={<StartView />} /> */}
        {/* <Route path="/login" element={<Login />} /> */}
      </Routes>
    </Router>
  );
}

export default App;
