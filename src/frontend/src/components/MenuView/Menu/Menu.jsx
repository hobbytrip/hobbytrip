import style from './Menu.module.css';
import { AiFillHome } from "react-icons/ai";
import { PiPlanetDuotone } from "react-icons/pi";
import { FaBell } from "react-icons/fa";
import { ImExit } from "react-icons/im";
import { IoSettings, IoCheckmarkCircleOutline, IoCheckmarkCircle } from "react-icons/io5";
import { useNavigate } from 'react-router-dom';

const User = () => {
  return(
    <>
    <div className={style.userContainer}>
      <div className={style.user}>
        <div className={style.imgContainer}>
          <img src='./../../image/default-logo.png' style={{backgroundColor: 'hotPink'}}/>
        </div>
        <div className={style.userData}>
          <h3> 유저 이름 </h3>
          <h6 style={{color: 'var(--mid-gray)'}}> 상태 </h6>
        </div>
      </div>
    </div>
    </>
  )
}

const MenuList = () => {
  const navigate = useNavigate();
  
  return(
    <>
    <div className={style.menuListWrapper}>
      <button>
        <AiFillHome className={style.icon} />
        <h3> 홈 </h3>
      </button>
      <button>
        <PiPlanetDuotone className={style.icon} />
        <h3> 공개 행성 탐색하기 </h3>
      </button>
      <button>
        <IoSettings className={style.icon}/>
        <h3> 계정 설정 </h3>
      </button>
      <div className={style.alarm}>
        <FaBell className={style.icon} /> 
        <div>
          <h3> 알림 설정 </h3>
          <h5 style={{color: 'var(--dark-gray)'}}> 채팅 메세지, 초대 알림 활성화 </h5>
          <h5 style={{color: 'var(--mid-gray)'}}> 원활한 서비스 이용을 위해 알림 활성화를 권장합니다. </h5>
        </div>
        <button>
          <IoCheckmarkCircle className={style.icon} />
        </button>
      </div>
      <button>
        <ImExit className={style.icon} /><h3> 로그아웃 </h3>
      </button>
    </div>
    </>
  )
}

const Menu = () =>{
  return(
    <div className={style.wrapper}>
      <User />
      <MenuList />
    </div>
  )
}

export default Menu;