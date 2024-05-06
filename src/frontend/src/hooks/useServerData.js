import { useState, useEffect } from "react";
import useAxios from "../utils/instance";

const useServerData = (serverId, userId) => {
  const axios = useAxios();
  const [serverData, setServerData] = useState({
    serverInfo: null,
    serverCategories: null,
    serverChannels: null,
    userStatus: null,
    messages: null,
  });

  useEffect(() => {
    const fetchServerData = async () => {
      try {
        const response = await axios.get(
          `/api/community/server/${serverId}/${userId}`
        );
        const responseData = response.data.data;
        setServerData({
          serverInfo: responseData.server,
          serverCategories: responseData.categories,
          serverChannels: responseData.channels,
          userStatus: responseData.userOnOff,
          messages: responseData.messages,
        });
      } catch (error) {
        console.error("Error fetching server Data:", error);
      }
    };

    // 직전 serverId, userId 저장
    let prevServerId = serverId;
    let prevUserId = userId;

    // 서버 아이디나 유저 아이디가 변경될 때마다 데이터를 새로 GET
    if (serverId !== prevServerId || userId !== prevUserId) {
      fetchServerData();
      prevServerId = serverId;
      prevUserId = userId;
    }
  }, []); //의존성 배열 사용X

  return serverData;
};

export default useServerData;
