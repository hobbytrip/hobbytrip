import s from "./ChatRoomInfo.module.css";
import { TiUserAdd, TiGroup } from "react-icons/ti";
import { RiSettings3Fill } from "react-icons/ri";
import { useEffect } from "react";
import usePlanetIcon from "../../../../hooks/usePlanetIcon";
import { useNavigate } from "react-router-dom";
import useServerStore from "../../../../actions/useServerStore";

export default function ChatHeader({}) {
  const nav = useNavigate();
  const [planetIcon, getRandomPlanetIcon] = usePlanetIcon();
  useEffect(() => {
    getRandomPlanetIcon();
  }, []);
  // 서버 정보를 불러와서 이름에 띄워줌
  const { serverData } = useServerStore((state) => ({
    serverData: state.serverData,
  }));

  const { serverInfo } = serverData;
  console.error(serverInfo);

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
          {serverInfo.name ? serverInfo.name : null}
        </h2>
      </div>
      <div className={s.modals}>
        <TiUserAdd className={s.modal} />
        <TiGroup className={s.modal} />
        <RiSettings3Fill
          className={s.modal}
          onClick={() => nav(`/${serverInfo.serverId}/setting`)}
        />
      </div>
    </div>
  );
}