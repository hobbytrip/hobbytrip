import { useEffect, useState } from "react";
import style from "./MyPlanet.module.css";
import { IoIosArrowBack, IoIosArrowForward } from "react-icons/io";
import { FaPlus } from "react-icons/fa6";
import { AiOutlineClose } from "react-icons/ai";
import usePlanetIcon from "../../../hooks/usePlanetIcon";
import CreateServer from "../../Modal/ServerModal/CreateServer/CreateServer";
import { useNavigate } from "react-router-dom";

const Leftbtn = ({ onClick }) => {
  return (
    <button className={style.leftBtn} onClick={onClick}>
      <IoIosArrowBack
        style={{ width: "15px", height: "15px", color: "#D9D9D9" }}
      />
    </button>
  );
};

const Rightbtn = ({ onClick }) => {
  return (
    <button className={style.rightBtn} onClick={onClick}>
      <IoIosArrowForward
        style={{ width: "15px", height: "15px", color: "#D9D9D9" }}
      />
    </button>
  );
};

const CreatePlanetbtn = ({ onClick }) => {
  return (
    <button className={style.createPlanet} onClick={onClick}>
      <FaPlus
        className={style.plusIcon}
        style={{ width: "16px", height: "16px", color: "#EDEDED" }}
      />
    </button>
  );
};

const MyPlanet = ({ servers }) => {
  const [currentPage, setCurrentPage] = useState(0);
  const [planetIcon, getRandomPlanetIcon] = usePlanetIcon();
  const [showCreateServer, setShowCreateServer] = useState(false);
  const nav = useNavigate();

  const serversPerPage = 4;
  const startIndex = currentPage * serversPerPage;
  const endIndex = Math.min(startIndex + serversPerPage, (servers || []).length);

  useEffect(() => {
    getRandomPlanetIcon();
  }, [getRandomPlanetIcon]);

  const handleCreateModalClick = () => {
    setShowCreateServer(true);
  };

  const handleCloseModal = () => {
    setShowCreateServer(false);
  };

  const handleLeft = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 0));
  };

  const handleRight = () => {
    setCurrentPage((prevPage) =>
      Math.min(prevPage + 1, Math.ceil((servers?.length || 0) / 4) - 1)
    );
  };

  const handleServerClick = (serverId) => {
    nav(`/server/${serverId}/menu`);
  };

  return (
    <div className={style.wrapper}>
      <h3> 내 행성 </h3>
      <div className={style.planetContainer}>
        <Leftbtn onClick={handleLeft} />
        <div className={style.planetList}>
          {(servers || []).slice(startIndex, endIndex).map((server) => (
            <div key={server.serverId} className={style.planetItem}>
              <button
                className={style.planetThumb}
                onClick={() => handleServerClick(server.serverId)}
              >
                {server.profile && (
                  <img
                    src={server.profile}
                    className={style.planetIcon}
                    alt="행성 이미지"
                  />
                )}
                <div className={style.serverName}>{server.name}</div>
              </button>
            </div>
          ))}
          <CreatePlanetbtn onClick={handleCreateModalClick} />
        </div>
        <Rightbtn onClick={handleRight} />
      </div>
      {showCreateServer && (
        <div className={style.createServerModal}>
          <CreateServer />
          <button className={style.closeBtn} onClick={handleCloseModal}>
            <h4 style={{ color: "#fff", textDecoration: "underline" }}>
              뒤로 가기
            </h4>
          </button>
        </div>
      )}
    </div>
  );
};

export default MyPlanet;
