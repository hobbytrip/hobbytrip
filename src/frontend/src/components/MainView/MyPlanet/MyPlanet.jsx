import { useEffect } from "react";
import style from "./MyPlanet.module.css";
import { IoIosArrowBack, IoIosArrowForward } from "react-icons/io";
import { FaPlus } from "react-icons/fa6";
import usePlanetIcon from "../../../hooks/usePlanetIcon";

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
const CreatePlanetbtn = () => {
  return (
    <button className={style.createPlanet}>
      <FaPlus
        className={style.plusIcon}
        style={{ width: "16px", height: "16px", color: "#EDEDED" }}
      />
    </button>
  );
};

const MyPlanet = () => {
  const [planetIcon, getRandomPlanetIcon] = usePlanetIcon();
  useEffect(() => {
    getRandomPlanetIcon();
  }, [getRandomPlanetIcon]);
  return (
    <div className={style.wrapper}>
      <h3> 내 행성 </h3>
      <div className={style.planetContainer}>
        <Leftbtn />
        <div className={style.planetList}>
          <div>
            <button className={style.planetThumb}>
              {" "}
              <img
                src={planetIcon}
                className={style.planetIcon}
                alt="행성이미지"
              />
            </button>
            <CreatePlanetbtn />
          </div>
        </div>
        <Rightbtn />
      </div>
    </div>
  );
};

export default MyPlanet;
