import s from "./NotificationBox.module.css";

function NotificationBox({ isNotification, handleChange }) {
  return (
    <div className={s.wrapper}>
      <h4 className={s.list}>알림 설정</h4>
      <div className={s.notiContainer}>
        <div className={s.notiBox}>
          <h4>채팅 메시지, 초대 알림 활성화</h4>
          <h4 className={s.detail}>
            원활한 서비스 이용을 위해 알림 활성화를 권장합니다.
          </h4>
        </div>
        <label className={s.checkLabel}>
          <input
            className={s.checkBox}
            type="checkbox"
            name="isNotification"
            checked={isNotification}
            onChange={handleChange}
          />
          <span className={s.checkIcon}></span>
        </label>
      </div>
    </div>
  );
}

export default NotificationBox;
