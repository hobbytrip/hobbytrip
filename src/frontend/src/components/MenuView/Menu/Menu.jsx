import style from './Menu.module.css';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useUserStore from '../../../actions/useUserStore';
import { AiFillHome } from "react-icons/ai";
import { PiPlanetDuotone } from "react-icons/pi";
import { FaBell } from "react-icons/fa";
import { ImExit } from "react-icons/im";
import { IoSettings, IoCheckmarkCircleOutline, IoCheckmarkCircle } from "react-icons/io5";
// import { axiosInstance } from '../../../utils/axiosInstance';
// import API from '../../../utils/API/API';

// IoCheckmarkCircleOutline - 체크 안 했을 때 (비어있음)
// IoCheckmarkCircle - 체크 했을 때 (색 채워짐)

const User = () => {
  const nickname = useUserStore(state => state.nickname);

  return(
    <>
    <div className={style.userContainer}>
      <div className={style.user}>
        <div className={style.imgContainer}>
          <img src='./../../image/default-logo.png' style={{backgroundColor: 'hotPink'}}/>
        </div>
        <div className={style.userData}>
          <h3> {nickname} </h3>
          <div className={style.statusContainer}>
            <div className={style.statusCircle}/>
            <h6 style={{color: 'var(--mid-gray)'}}> Online </h6>
          </div>
        </div>
      </div>
    </div>
    </>
  )
}

const MenuList = () => {
  const { notificationEnabled } = useUserStore(); 
  const [notification, setNotification] = useState(notificationEnabled);
  const nav = useNavigate();

  const handleHome = () => {
    nav("/main");
  }

  const handleAlarmToggle = () => {
    setNotification(!notification);
  }

  // const handleLogout = async () => {
  //   try {
  //     const accessToken = localStorage.getItem('accessToken');
  //     const refreshToken = localStorage.getItem("refreshToken");
  //     console.log(accessToken, refreshToken)

  //     const res = await axiosInstance.post(API.LOG_OUT, {
  //       "accesstoken": accessToken,
  //       "refreshtoken": refreshToken
  //     });

  //     console.log(res);
  //     nav("/");
  //   } catch (error) {
  //     console.error("Logout failed", error);
  //   }
  // }

  return(
    <>
    <div className={style.menuListWrapper}>
      <button onClick={handleHome}>
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
          <h5 style={{color: 'var(--dark-gray)'}}> 
            채팅 메세지, 초대 알림 활성화 
          </h5>
          <h5 style={{color: 'var(--mid-gray)'}}> 
            원활한 서비스 이용을 위해 알림 활성화를 권장합니다. 
          </h5>
        </div>
        <button onClick={handleAlarmToggle}>
          {notification ? (
            <IoCheckmarkCircle className={style.icon} />
            ) : (
            <IoCheckmarkCircleOutline 
              className={style.icon}
              style={{color: 'var(--dark-gray)'}} />
            )
          }
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
