import style from './MyPlanet.module.css';
import { IoIosArrowBack, IoIosArrowForward } from "react-icons/io";
import { FaPlus } from "react-icons/fa6";


const Leftbtn = () => {
  return (
    <button className={style.leftBtn}>
      <IoIosArrowBack style={{ width: '15px', height: '15px' }} />
    </button>
    );
}

const Rightbtn = () => {
  return (
    <button className={style.rightBtn}>
      <IoIosArrowForward style={{ width: '15px', height: '15px' }} />
    </button>
    );
}

const CreatePlanetbtn = () => {
  return (
    <button className={style.createPlanet}>
      <FaPlus style={{ width: '15px', height: '15px', color: '#EDEDED' }} />
    </button>
    );
}

const MyPlanet = () => {
  return (
    <div className={style.wrapper}>
      <div className={style.container}>
        <h3> 내 행성 </h3>
        <div className={style.planetContainer}>  
          <Leftbtn />
          <div className={style.planetList}>
            <div>  
              <button> B1 </button>
              <CreatePlanetbtn />
            </div>
          </div>
          <Rightbtn /> 
        </div> 
      </div>
    </div>
  );
}

export default MyPlanet;