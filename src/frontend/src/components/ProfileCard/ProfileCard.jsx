import React from "react";
import s from "./ProfileCard.module.css";
import DefaultIcon from "../Common/DefaultIcon";

function ProfileCard({ profile }) {
  return (
    <div className={s.profileCard}>
      <DefaultIcon profile={profile} />
      <div className={s.rightContainer}>
        <h3>{profile.name}</h3>
        <h4 className={s.statusMessage}>{profile.statusMessage}</h4>
      </div>
    </div>
  );
}

export default ProfileCard;
