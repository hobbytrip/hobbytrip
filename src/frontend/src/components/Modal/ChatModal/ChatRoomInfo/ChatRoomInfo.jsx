import s from "./ChatRoomInfo.module.css";
import { TiUserAdd, TiGroup } from "react-icons/ti";
import { RiSettings3Fill } from "react-icons/ri";
import { useEffect, useState } from "react";
import usePlanetIcon from "../../../../hooks/usePlanetIcon";
import { useNavigate } from "react-router-dom";
import useServerStore from "../../../../actions/useServerStore";
import InviteServer from "../../ServerModal/Servers/InviteServer/InviteServer";

export default function ChatHeader() {
  const nav = useNavigate();
  const [planetIcon, getRandomPlanetIcon] = usePlanetIcon();
  const [isInviteOpen, setInviteOpen] = useState(false);

  useEffect(() => {
    getRandomPlanetIcon();
  }, []);

  const { serverData } = useServerStore((state) => ({
    serverData: state.serverData,
  }));
  const name = serverData?.serverInfo?.name || "";

  const handleInviteClick = () => {
    setInviteOpen(true);
  };

  const handleInviteClose = () => {
    setInviteOpen(false);
  };

  return (
    <div className={s.wrapper}>
      <div className={s.infoBox}>
        {planetIcon && (
          <img src={planetIcon} className={s.serverIcon} alt="Server Planet Icon" />
        )}
        <h2 className={s.serverName}>{name}</h2>
      </div>
      <div className={s.modals}>
        <TiUserAdd className={s.modal} onClick={handleInviteClick} />
        <TiGroup className={s.modal} />
        <RiSettings3Fill className={s.modal} onClick={() => nav(`/${serverData.serverInfo.serverId}/setting`)} />
      </div>
      {isInviteOpen && <InviteServer userId={serverData.userId} onClose={handleInviteClose} />}
    </div>
  );
}
