import { useEffect, useState } from "react";
import useUserStore from "../../actions/useUserStore.js";
import API from "../../utils/API/API.js";
import { axiosInstance } from "../../utils/axiosInstance.js";
import MainHeader from "../../components/MainView/MainHeader/MainHeader.jsx";
import MyPlanet from "../../components/MainView/MyPlanet/MyPlanet.jsx";
import MyFriend from "../../components/Modal/FriendModal/MyFriend/MyFriend.jsx";
import style from "./MainView.module.css";
import setSSE from "../../hooks/useSSE.js";

const MainView = () => {
  const [servers, setServers] = useState(null);
  const [dms, setDms] = useState(null);
  const { userId } = useUserStore();

  setSSE();
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
      // setServer();
      getMainData();
    }
  }, [userId]);

  if (!userId) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <div className={style.wrapper}>
        <div className={style.container}>
          <MainHeader />
          <MyPlanet servers={servers} />
          <MyFriend />
        </div>
      </div>
    </>
  );
};

export default MainView;
