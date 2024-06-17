import { useEffect, useState } from "react";
import useUserStore from "../../actions/useUserStore.js";
import API from "../../utils/API/API.js";
import { axiosInstance } from "../../utils/axiosInstance.js";
import MainHeader from "../../components/MainView/MainHeader/MainHeader.jsx";
import MyPlanet from "../../components/MainView/MyPlanet/MyPlanet.jsx";
import MyFriend from "../../components/Modal/FriendModal/MyFriend/MyFriend.jsx";
import style from "./MainView.module.css";
import setSSE from "../../hooks/useSSE.js";
import DmHistoryList from "../../components/TYPE/DM/DmHistoryList/DmHistoryList.jsx";
import { AiOutlineMenu } from "react-icons/ai";
import { useNavigate } from "react-router-dom";
import usePlanetsStore from "../../actions/usePlantesStore.js";

const MainView = () => {
  const [dms, setDms] = useState(null);
  const { userId } = useUserStore();
  const { setServers } = usePlanetsStore();

  // setSSE();
  useEffect(() => {
    if (userId) {
      const getMainData = async () => {
        try {
          const response = await axiosInstance.get(API.READ_MAIN(userId));
          const resData = response.data.data;
          console.log(resData);
          setServers(resData.servers);
          setDms(resData.dms);
        } catch (error) {
          console.error("Error fetching server Data:", error);
        }
      };
      getMainData();
    }
  }, [userId, setServers]);

  if (!userId) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <div className={style.wrapper}>
        <header className={style.header}/>  
        <div className={style.container}>
          <div className={style.deskServers}>
            <MainHeader className={style.mainHeader} />
            <MyPlanet className={style.myPlanet} />
          </div>
          <div className={style.deskDms}>
            <Menu />
            <DmHistoryList dmHistoryList={dms} />
          </div>
          <MyFriend className={style.myFriend} />
        </div>
      </div>
    </>
  );
};

const Menu = () => {
  const nav = useNavigate();
  function handleGoToMenu(){
    nav('/menu');
  }
  return(
    <div className={style.menuContainer}>
      <button onClick={handleGoToMenu}>
        <AiOutlineMenu className={style.icon}/>
        <span className={style.text}> 메뉴 </span>      
      </button>
    </div>
  )
}

export default MainView;
