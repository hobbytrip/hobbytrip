import { create } from "zustand";
import API from "../utils/API/API";
import { axiosInstance } from "../utils/axiosInstance";

const useServerStore = create((set) => ({
  serverData: {
    serverInfo: null,
    serverCategories: null,
    serverChannels: null,
    userStatus: null,
    messages: null,
  },
  setServerData: (newServerData) => set({ serverData: newServerData }),
  fetchServerData: async (serverId, userId) => {
    try {
      const res = await axiosInstance.get(API.GET_SERVER(serverId, userId));
      const resData = res?.data?.data;
      console.error(resData);

      if (resData) {
        set({
          serverData: {
            serverInfo: resData.server,
            serverCategories: resData.categories,
            serverChannels: resData.channels,
            userStatus: resData.usersState,
            messages: resData.messages,
          },
        });
      } else {
        console.error("Error: Data not found in res", res);
        // 초기 상태로 설정
        set({
          serverData: {
            serverInfo: null,
            serverCategories: null,
            serverChannels: null,
            userStatus: null,
            messages: null,
          },
        });
      }
    } catch (error) {
      console.error("Error fetching server data:", error);
      // 초기 상태로 설정
      set({
        serverData: {
          serverInfo: null,
          serverCategories: null,
          serverChannels: null,
          userStatus: null,
          messages: null,
        },
      });
    }
  },
}));

export default useServerStore;
