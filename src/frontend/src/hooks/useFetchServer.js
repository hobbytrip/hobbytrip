import { axiosInstance } from "../utils/axiosInstance";
import API from "../utils/API/API";
import useServerStore from "../actions/useServerStore";

const useFetchServer = async (serverId, userId) => {
  const { setServerData } = useServerStore.getState();
  try {
    const res = await axiosInstance.get(API.GET_SERVER(serverId, userId));
    const data = res.data.data;
    console.log(data);
    if (data) {
      setServerData(data);
    }
  } catch (error) {
    console.error("getServerError:", error);
  }
};

export default useFetchServer;
