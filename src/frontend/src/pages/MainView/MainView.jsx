import MainHeader from "../../components/MainView/MainHeader/MainHeader.jsx";
import MyPlanet from "../../components/MainView/MyPlanet/MyPlanet.jsx";
import MyFriend from "../../components/MainView/MyFriend/MyFriend.jsx";
import style from "./MainView.module.css";
import { useEffect, useState } from "react";
import useUserStore from "../../actions/useUserStore.js";
import API from "../../utils/API/TEST_API.js";
import { axiosInstance } from "../../utils/axiosInstance.js";

const MainView = () => {
  const [servers, setServers] = useState(null);
  const [dms, setDms] = useState(null);
  const { user, getUserInfo } = useUserStore((state) => ({
    user: state.user,
    getUserInfo: state.getUserInfo,
  }));

  useEffect(() => {
    if (!user) {
      getUserInfo();
    }
  }, [user, getUserInfo]);

  useEffect(() => {
    if (user?.userId) {
      const getMainData = async () => {
        try {
          const response = await axiosInstance.get(`/user/${user.userId}`);
          const resData = response.data.data;
          console.log(resData);
          setServers(resData.servers || []);
          setDms(resData.dms);
        } catch (error) {
          console.error("Error fetching server Data:", error);
        }
      };
      getMainData();
    }
  }, [user?.userId]);

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
