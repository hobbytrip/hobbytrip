import { useEffect, useState } from "react";
import useUserStore from "../../actions/useUserStore.js";
import API from "../../utils/API/TEST_API.js";
import { axiosInstance } from "../../utils/axiosInstance.js";
import MainHeader from "../../components/MainView/MainHeader/MainHeader.jsx";
import MyPlanet from "../../components/MainView/MyPlanet/MyPlanet.jsx";
import MyFriend from "../../components/MainView/MyFriend/MyFriend.jsx";
import style from "./MainView.module.css";

const MainView = () => {
  const [servers, setServers] = useState(null);
  const [dms, setDms] = useState(null);
  const { userId } = useUserStore();

  useEffect(() => {
    if (userId) {
      const getMainData = async () => {
        try {
          const response = await axiosInstance.get(API.READ_MAIN(userId));
          const resData = response.data.data;
          console.error(resData);
          setServers(resData.servers || []);
          setDms(resData.dms);
        } catch (error) {
          console.error("Error fetching server Data:", error);
        }
      };
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
