import style from './Menu.module.css';
import { AiFillHome } from "react-icons/ai";
import { PiPlanetDuotone } from "react-icons/pi";
import { FaBell } from "react-icons/fa";
import { ImExit } from "react-icons/im";
import { IoSettings, IoCheckmarkCircleOutline, IoCheckmarkCircle } from "react-icons/io5";
import { useNavigate } from 'react-router-dom';
// IoCheckmarkCircleOutline - 체크 안 했을 때 (비어있음)
// IoCheckmarkCircle - 체크 했을 때 (색 채워짐)
// 용으로 일단 같이 받아놨는데 편한대로 사용하거나 삭제해주세용


// userContainer: 유저의 큰 틀 - 높이, 넓이, 패딩 맞춤용
// user: 유저의 작은 틀
// imgContainer: 유저 이미지 - 임의로 default-logo 삽입
// userData: 유저 이름, 상태 담는 틀
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

// menuListWrapper: 메뉴 리스트를 담는 틀 
// icon: 아이콘 크기, 색 설정용 클래스
// alarm: 알람 설정 - 다른 건 button으로 했는데 div로 구현
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

// wrapper: 유저 & 메뉴 창의 틀
// User: 유저 사진, 이름, 상태
// MenuList: 홈, 공개 행성 등의 메뉴 리스트
const Menu = () =>{
  return(
    <div className={style.wrapper}>
      <User />
      <MenuList />
    </div>
  )
}

export default Menu;