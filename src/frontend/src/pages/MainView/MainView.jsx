import MainHeader from '../../components/MainView/MainHeader/MainHeader.jsx';
import MyPlanet from '../../components/MainView/MyPlanet/MyPlanet.jsx';
import MyFriend from '../../components/MainView/MyFriend/MyFriend.jsx';
import style from './MainView.module.css';
import axios from 'axios';
import { useEffect, useState } from 'react';
import useUserStore from '../../actions/useUserStore.js';
import API from '../../utils/API/API.js';

const MainView = () => {
  const [servers, setServers] = useState(null);
  const [dms, setDms] = useState(null);
  const { user } = useUserStore((state) => ({
    user: state.user
  }));
  const userId = 1; 

  const URL = API.COMM;

  useEffect(() => {
    const getMainData = async () => {
      try {
        const response = await axios.get(
          `${URL}/user/${userId};`
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