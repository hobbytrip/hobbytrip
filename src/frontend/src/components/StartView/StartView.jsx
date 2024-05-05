import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import s from "./StartView.module.css";

function StartView() {
  const url = "../../assets/image/logo.png";
  const navigate = useNavigate();

  useEffect(() => {
    setTimeout(() => {
      navigate("/login");
    }, 3000);
  }, [navigate]);

  return (
    <div className={s.wrapper}>
      <img src="./image/logo.png" className={s.logo} alt="logo" />
      <h3 className={s.startText}>함께하는 운동 커뮤니티</h3>
    </div>
  );
}

export default StartView;
