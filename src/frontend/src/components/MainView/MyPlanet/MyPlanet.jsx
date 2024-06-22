import { useEffect, useState } from "react";
import style from "./MyPlanet.module.css";
import { IoIosArrowBack, IoIosArrowForward } from "react-icons/io";
import { FaPlus } from "react-icons/fa6";
import useServerStore from "../../../actions/useServerStore"; 
import CreateServer from "../../Modal/ServerModal/Servers/CreateServer/CreateServer";
import { useNavigate } from "react-router-dom";
import useUserStore from "../../../actions/useUserStore";
import usePlanetsStore from "../../../actions/usePlantesStore";
import { axiosInstance } from "../../../utils/axiosInstance";
import API from "../../../utils/API/API";

const Leftbtn = ({ onClick }) => (
  <button className={style.leftBtn} onClick={onClick}>
    <IoIosArrowBack
      style={{ width: "15px", height: "15px", color: "#D9D9D9" }}
    />
  </button>
);

const Rightbtn = ({ onClick }) => (
  <button className={style.rightBtn} onClick={onClick}>
    <IoIosArrowForward
      style={{ width: "15px", height: "15px", color: "#D9D9D9" }}
    />
  </button>
);

const CreatePlanetbtn = ({ onClick }) => (
  <button className={style.createPlanet} onClick={onClick}>
    <FaPlus
      className={style.plusIcon}
      style={{ width: "16px", height: "16px", color: "#EDEDED" }}
    />
  </button>
);

const MyPlanet = () => {
  const [currentPage, setCurrentPage] = useState(0);
  const [showCreateServer, setShowCreateServer] = useState(false);
  const [innerWidth, setInnerWidth] = useState(window.innerWidth);
  const [innerHeight, setInnerHeight] = useState(window.innerHeight);
  const nav = useNavigate();
  const { serverData } = useServerStore.getState();
  const { setServerData } = useServerStore((state) => ({
    setServerData: state.setServerData
  }))
  const { USER } = useUserStore();
  const userId = USER.userId;
  const { servers } = usePlanetsStore(); 

  let serversPerPage;

  const resizeListener = () => {
    setInnerWidth(window.innerWidth);
    setInnerHeight(window.innerHeight);
  };

  useEffect(() => {
    window.addEventListener("resize", resizeListener);

    return () => {
      window.removeEventListener("resize", resizeListener);
    };
  }, []);

  if (innerWidth >= 432) {
    serversPerPage = Math.floor((innerHeight - 86) / 80) - 2;
  } else {
    serversPerPage = 4;
  }

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
      Math.min(
        prevPage + 1,
        Math.ceil((servers?.length || 0) / serversPerPage) - 1
      )
    );
  };

  const handleServerClick = async (serverId) => {
    try {
      const res = await axiosInstance.get(API.GET_SERVER(serverId, userId));
      const data = res.data.data;
      console.error(data);
      if (data) {
        setServerData(data);
        console.error(serverData);
        const defaultChatChannel = serverData.serverChannels.find(
          (channel) => channel.channelType === "CHAT"
        );
        if (defaultChatChannel) {
          nav(`/${serverId}/${defaultChatChannel.channelId}/chat`);
        }
      }
    } catch (error) {
      console.error("Error fetching server data:", error);
    }
  };

  return (
    <div className={style.wrapper}>
      <h3 style={{ fontFamily: "DOSSaemmul" }}> 내 행성 </h3>
      <div className={style.planetContainer}>
        <Leftbtn onClick={handleLeft} />
        <div className={style.planetList}>
          {(servers || [])
            .slice(
              currentPage * serversPerPage,
              (currentPage + 1) * serversPerPage
            )
            .map((server) => {
              return (
                <div key={server.serverId} className={style.planetItem}>
                  <button
                    className={style.planetThumb}
                    onClick={() => handleServerClick(server.serverId)}
                  >
                    {server.profile !== "null" && server.profile !== null ? (
                      <img
                        src={server.profile}
                        className={style.planetIcon}
                        alt=""
                      />
                    ) : (
                      <div className={style.serverName}>{server.name}</div>
                    )}
                  </button>
                </div>
              );
            })}
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
