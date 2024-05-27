import MainHeader from '../../components/MainView/MainHeader/MainHeader.jsx';
import MyPlanet from '../../components/MainView/MyPlanet/MyPlanet.jsx';
import MyFriend from '../../components/MainView/MyFriend/MyFriend.jsx';
import style from './MainView.module.css';
import axios from 'axios';
import { useEffect, useState } from 'react';

const MainView = () => {
  const [servers, setServers] = useState(null);
  const [dms, setDms] = useState(null);
  const userId = 1; // 테스트용

  useEffect(() => {
    const getMainData = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/user/${userId}`
        ); 
        const resData = response.data.data;
        console.log(resData);
        setServers(resData.servers || []);
        setDms(resData.dms);
      } catch (error) {
        console.error("Error fetching server Data:", error);
      }
    }
    getMainData();
  }, []);
  return( 
    <>
    <div className={style.wrapper}>
      <div className={style.container}>
        <MainHeader />
        <MyPlanet servers={servers}/>
        <MyFriend />
      </div>
    </div>
    </>
  )
}

export default MainView;