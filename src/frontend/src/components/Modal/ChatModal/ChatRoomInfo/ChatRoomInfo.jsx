import s from "./ChatRoomInfo.module.css";
import { TiUserAdd, TiGroup } from "react-icons/ti";

import { RiSettings3Fill } from "react-icons/ri";
import { useEffect } from "react";
import usePlanetIcon from "../../../../hooks/usePlanetIcon";

export default function ChatHeader() {
  const [planetIcon, getRandomPlanetIcon] = usePlanetIcon();
  useEffect(() => {
    getRandomPlanetIcon();
  }, []);
  //임시 테스트로 랜덤 서버 이미지 불러옴
  return (
    <div className={s.wrapper}>
      <div className={s.infoBox}>
        {planetIcon && (
          <img
            src={planetIcon}
            className={s.serverIcon}
            alt="Server Planet Icon"
          />
        )}
        <h2 className={s.serverName}>헬스 자세교정</h2>
      </div>
      <div className={s.modals}>
        <TiUserAdd className={s.modal} />
        <TiGroup className={s.modal} />
        <RiSettings3Fill className={s.modal} />
      </div>
    </div>
  );
}
