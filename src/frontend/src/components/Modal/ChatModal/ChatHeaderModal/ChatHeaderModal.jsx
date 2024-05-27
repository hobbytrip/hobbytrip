import s from "./ChatHeader.module.css";
import { TiUserAdd, TiGroup } from "react-icons/ti";
import { RiSettings3Fill } from "react-icons/ri";
import { useEffect } from "react";
import usePlanetIcon from "../../../../hooks/usePlanetIcon";
import { useNavigate } from "react-router-dom";

export default function ChatHeader({ name, id }) {
  const nav = useNavigate();
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
        <h2 className={s.serverName}>
          {name ? name : null}
        </h2>
      </div>
      <div className={s.modals}>
        <TiUserAdd className={s.modal} />
        <TiGroup className={s.modal} />
        <RiSettings3Fill className={s.modal} 
          // serverId는 서버 아이디로 바꿔주기
          onClick={() => nav(`/server/${id}/setting`)} /> 
      </div>
    </div>
  );
}
