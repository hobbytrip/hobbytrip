import React, { useState } from "react";
import s from "./UserProfileView.module.css";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import useUserStore from "../../actions/useUserStore";
import NotificationBox from "../../components/NotificationBox/NotificationBox";

function UserProfileView() {
  const { notificationEnabled, updateUserInfo } = useUserStore();
  const [isEditingStatusMsg, setIsEditingStatusMsg] = useState(false);
  const [editedStatusMsg, setEditedStatusMsg] = useState("");
  const [editedNoti, setEditedNoti] = useState(notificationEnabled);
  const { USER } = useUserStore();

  const handleStatusMsgChange = (e) => {
    setEditedStatusMsg(e.target.value);
  };

  const handleSaveStatusMsg = async () => {
    try {
      await updateUserInfo("statusMessage", { statusMessage: editedStatusMsg });
      setUSER((prev) => ({ ...prev, statusMessage: editedStatusMsg }));
      setIsEditingStatusMsg(false);
      console.log(editedStatusMsg);
    } catch (error) {
      console.error("Status message update failed", error);
    }
  };

  const handleChangeNoti = async (e) => {
    const isEnabled = e.target.checked;
    setEditedNoti(isEnabled);
    updateUserInfo("notice", { notice: isEnabled });
    console.log(isEnabled);
  };

  if (!USER.userId) {
    return <p>Loading...</p>;
  }
  return (
    <div className={s.wrapper}>
      <div className={s.container}>
        <h2 className={s.title}>내 계정</h2>
        <div className={s.profileContainer}>
          <>
            <div className={s.profileCard}>
              <ProfileCard user={USER} />
            </div>
            <div className={s.form}>
              <div className={s.infos}>
                <h3>사용자명</h3>
                <h4>{USER.name}</h4>
              </div>
            </div>
            <div className={s.form}>
              <div className={s.infos}>
                <h3>한줄소개</h3>
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
                      <h4>{USER.statusMessage}</h4>
                    </div>
                    <button
                      className={s.profileBtn}
                      onClick={() => {
                        setEditedStatusMsg(USER.statusMessage);
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
                <h3>프로필 사진 변경하기</h3>
                <div className={s.buttonContainer}>
                  <button className={s.uploadImgBtn}>이미지 업로드</button>
                  <button className={s.deleteImgBtn}>이미지 삭제하기</button>
                </div>
              </div>
            </div>
            <div className={s.form}>
              <div className={s.infos}>
                <h3>이메일</h3>
                <h4>{USER.email}</h4>
              </div>
            </div>
            <NotificationBox
              notificationEnabled={editedNoti}
              handleChange={handleChangeNoti}
            />
          </>
        </div>
      </div>
    </div>
  );
}

export default UserProfileView;
