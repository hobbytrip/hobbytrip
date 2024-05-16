import React, { useState, useEffect } from "react";
import s from "./UserProfileView.module.css";
import useAxios from "../../utils/instance";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import useUserStore from "../../actions/useUserStore";
import NotificationBox from "../../components/NotificationBox/NotificationBox";

function UserProfileView() {
  // const axios = useAxios();
  const user = useUserStore((state) => state.user);
  const { updateUserInfo } = useUserStore();
  const [isEditingName, setIsEditingName] = useState(false);
  const [isEditingStatusMsg, setIsEditingStatusMsg] = useState(false);
  const [editedName, setEditedName] = useState("");
  const [editedStatusMsg, setEditedStatusMsg] = useState("");
  const [editedNoti, setEditedNoti] = useState(
    user ? user.notificationEnabled : false
  );
  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await axios.get("/user/profile");
        if (response.status == 200) {
          setUserInfo(response.data);
          console.log(response.data);
        } else {
          console.error("No user data returned");
        }
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };

    fetchUserData();
  }, [user, setUserInfo]);
  
  const handleNameChange = (e) => {
    setEditedName(e.target.value);
  };

  const handleStatusMsgChange = (e) => {
    setEditedStatusMsg(e.target.value);
  };

  const handleSaveName = () => {
    updateUserInfo("name", { name: editedName });
    setIsEditingName(false);
    console.log(editedName);
  };

  const handleSaveStatusMsg = () => {
    updateUserInfo("statusMessage", { statusMessage: editedStatusMsg });
    setIsEditingStatusMsg(false);
    console.log(editedStatusMsg);
  };
  const handleChangeNoti = async (e) => {
    const isEnabled = e.target.checked;
    setEditedNoti(isEnabled);
    console.log(isEnabled);
    updateUserInfo("notificationEnabled", {
      notification: editedNoti,
    });
  };

  if (!user) {
    return <p>Loading...</p>;
  }

  return (
    <div className={s.wrapper}>
      <div className={s.container}>
        <h2 className={s.title}>내 계정</h2>
        <div className={s.profileContainer}>
          <>
            <ProfileCard profile={user} />
            <div className={s.form}>
              <div className={s.infos}>
                <a2>사용자명</a2>
                {isEditingName ? (
                  <>
                    <div className={s.wr}>
                      <input
                        value={editedName}
                        onChange={handleNameChange}
                        className={s.inputBox}
                      />
                      <button className={s.profileBtn} onClick={handleSaveName}>
                        저장
                      </button>
                    </div>
                  </>
                ) : (
                  <div className={s.wr}>
                    <div className={s.profileInfoBox}>
                      <h4>{user.name}</h4>
                    </div>
                    <button
                      className={s.profileBtn}
                      onClick={() => {
                        setEditedName(user.name);
                        setIsEditingName(true);
                      }}
                    >
                      수정
                    </button>
                  </div>
                )}
              </div>
            </div>
            <div className={s.form}>
              <div className={s.infos}>
                <a2>한줄소개</a2>
                {isEditingStatusMsg ? (
                  <div className={s.wr}>
                    <input
                      value={editedStatusMsg}
                      onChange={handleStatusMsgChange}
                      className={s.inputBox}
                    />
                    <button
                      className={s.profileBtn}
                      onClick={handleSaveStatusMsg}
                    >
                      저장
                    </button>
                  </div>
                ) : (
                  <div className={s.wr}>
                    <div className={s.profileInfoBox}>
                      <h4>{user.statusMessage}</h4>
                    </div>
                    <button
                      className={s.profileBtn}
                      onClick={() => {
                        setEditedStatusMsg(user.statusMessage);
                        setIsEditingStatusMsg(true);
                      }}
                    >
                      수정
                    </button>
                  </div>
                )}
              </div>
            </div>
            <div className={s.form}>
              <div className={s.infos}>
                <a2>프로필 사진 변경하기</a2>
                <div className={s.buttonContainer}>
                  <button className={s.uploadImgBtn}>이미지 업로드</button>
                  <button className={s.deleteImgBtn}>이미지 삭제하기</button>
                </div>
              </div>
            </div>
            <div className={s.form}>
              <div className={s.infos}>
                <a2>이메일</a2>
                <h4>{user.email}</h4>
              </div>
            </div>
            <NotificationBox
              notificationEnabled={user.notificationEnabled}
              handleChange={handleChangeNoti}
            />
          </>
        </div>
        {/* <button className={s.changePwd}>비밀번호 변경하기</button> */}
      </div>
    </div>
  );
}

export default UserProfileView;
