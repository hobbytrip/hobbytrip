import s from "./DefaultIcon.module.css";
function DefaultIcon({ profile }) {
  return (
    <div className={s.defaultImg}>
      <img
        className={s.profileImg}
        src={profile.profileImage}
        alt="ProfileImg"
        style={{ width: "36px", height: "36px" }}
      />
    </div>
  );
}

export default DefaultIcon;
