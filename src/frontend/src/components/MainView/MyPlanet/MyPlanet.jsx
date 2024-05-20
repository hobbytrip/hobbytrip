import { useEffect, useState } from "react";
import style from "./MyPlanet.module.css";
import { IoIosArrowBack, IoIosArrowForward } from "react-icons/io";
import { FaPlus } from "react-icons/fa6";
import { AiOutlineClose } from "react-icons/ai";
import usePlanetIcon from "../../../hooks/usePlanetIcon";
import CreateServer from "../../Modal/ServerModal/CreateServer/CreateServer";

const Leftbtn = () => {
  return (
    <button className={style.leftBtn}>
      <IoIosArrowBack
        style={{ width: "15px", height: "15px", color: "#D9D9D9" }}
      />
    </button>
  );
};
const Rightbtn = () => {
  return (
    <button className={style.rightBtn}>
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

const MyPlanet = () => {
  const [planetIcon, getRandomPlanetIcon] = usePlanetIcon();
  const [showCreateServer, setShowCreateServer] = useState(false);

  useEffect(() => {
    getRandomPlanetIcon();
  }, [getRandomPlanetIcon]);

  const handleCreateModalClick = () => {
    setShowCreateServer(true);
  };
  const handleCloseModal = () => {
    setShowCreateServer(false);
  };
  return (
    <div className={style.wrapper}>
      <h3> 내 행성 </h3>
      <div className={style.planetContainer}>
        <Leftbtn />
        <div className={style.planetList}>
          <div>
            <button className={style.planetThumb}>
              <img
                src={planetIcon}
                className={style.planetIcon}
                alt="행성이미지"
              />
            </button>
            <CreatePlanetbtn onClick={handleCreateModalClick} />
          </div>
        </div>
        <Rightbtn />
      </div>
      {showCreateServer && (
        <div className={style.createServerModal}>
          <CreateServer />
          <button className={style.closeBtn} onClick={handleCloseModal}>
            {/* <AiOutlineClose
              style={{ width: "20px", height: "20px", color: "#FFFFFF" }}
            ,/> */}
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
