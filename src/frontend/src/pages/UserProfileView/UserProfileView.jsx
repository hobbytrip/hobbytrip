import { useState, useEffect } from "react";
import s from "./UserProfileView.module.css";
import useAxios from "../../utils/instance";

function UserProfileView() {
  const axios = useAxios();
  const [profile, setProfile] = useState(null);

  const getProfileInfo = async () => {
    try {
      const response = await axios.get("http://localhost:3001/profile");
      console.log(response.data);
      setProfile(response.data);
    } catch (error) {
      console.error("사용자 프로필 정보 조회 오류", error);
    }
  };

  useEffect(() => {
    getProfileInfo();
  }, []);

  return (
    <div className={s.wrapper}>
      <h2 className={s.title}>내 계정</h2>
      <div className={s.container}>
        <div className={s.profileContainer}>
          {profile ? (
            <div>
              <h3>Name: {profile.name}</h3>
              <p>Email: {profile.email}</p>
              <p>Nickname: {profile.nickname}</p>
              <img
                src={profile.profileImage}
                alt="Profile"
                style={{ width: "100px", height: "100px" }}
              />
              <p>Phone: {profile.phone}</p>
              <p>Status: {profile.statusMessage}</p>
              <p>
                Last Updated: {new Date(profile.modifiedAt).toLocaleString()}
              </p>
            </div>
          ) : (
            <p>Loading profile...</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default UserProfileView;
