import React, { useState } from "react";
import s from "./RegView.module.css";
import RegForm from "../../components/RegForm/RegForm";

function RegView() {
  return (
    <div className={s.wrapper}>
      <div className={s.container}>
        <div className={s.contents}>
          <img src="./image/logo.png" className={s.logo} alt="logo" />
          <h2 className={s.description}>계정 만들기</h2>
        </div>
        <div className={s.RegForm}>
          <RegForm />
        </div>
      </div>
    </div>
  );
}

export default RegView;
