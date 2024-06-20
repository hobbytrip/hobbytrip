import API from "../API/API";
import { axiosInstance } from "../axiosInstance";
import useUserStore from "../../actions/useUserStore";
import useWebSocketStore from "../../actions/useWebSocketStore";

const getUser = async () => {
  const { setUserInfo } = useUserStore.getState();
  try {
    const response = await axiosInstance.get(API.GET_USER_PROFLIE);
    setUserInfo(response.data);
    useWebSocketStore.getState().connect(response.data.userId);
    console.log("유저 정보 get", response.data);
  } catch (err) {
    console.error("유저 프로필 정보 GET 실패", err);
  }
};

export default getUser;
