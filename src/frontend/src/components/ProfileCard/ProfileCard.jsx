import React from "react";
import s from "./ProfileCard.module.css";
import DefaultIcon from "../Common/DefaultIcon/DefaultIcon";

function ProfileCard({ user }) {
  return (
    <div className={s.profileCard}>
      <DefaultIcon profile={user} />
      <div className={s.rightContainer}>
        <h3>{user.name}</h3>
        <h4 className={s.statusMessage}>{user.statusMessage}</h4>
      </div>
    </div>
  );
}

export default ProfileCard;
