import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MediaView from "./pages/signalView/MediaView";

function App() {
  return (
    <Router>
      <Routes>
        {/* <Route path="/" element={<StartView />} /> */}
        {/* <Route path="/login" element={<Login />} /> */}
        <Route path="/vid" element={<MediaView />}/>
      </Routes>
    </Router>
  );
}

export default App;
