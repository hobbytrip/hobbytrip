import { create } from "zustand";
import API from "../utils/API/API";
import { axiosInstance } from "../utils/axiosInstance";

const useServerStore = create((set) => ({
  serverData: {
    serverInfo: null,
    serverCategories: null,
    serverChannels: null,
    serverUserInfos: null,
    userStatus: null,
    messages: null,
  },
  setServerData: (newServerData) => set({ serverData: newServerData }),
  setServerInfo: (newServerInfo) => set({
    serverData: {
      serverInfo: newServerInfo,
    },
  }),
  fetchServerData: async (serverId, userId) => {
    try {
      const accessToken = localStorage.getItem("accessToken");
      console.log(serverId, userId);
      const res = await axiosInstance.get(API.GET_SERVER(serverId, userId), {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      const resData = res?.data?.data;
      console.error(resData);

      if (resData) {
        set({
          serverData: {
            serverInfo: resData.server,
            serverCategories: resData.categories,
            serverChannels: resData.channels,
            serverUserInfos: resData.serverUserInfos,
            userStatus: resData.usersState,
            messages: resData.messages,
          },
        });
      } else {
        console.error("Error: Data not found in res", res);
        set({
          serverData: {
            serverInfo: null,
            serverCategories: null,
            serverChannels: null,
            serverUserInfos: null,
            userStatus: null,
            messages: null,
          },
        });
      }
    } catch (error) {
      console.error("Error fetching server data:", error);
      set({
        serverData: {
          serverInfo: null,
          serverCategories: null,
          serverChannels: null,
          serverUserInfos: null,
          userStatus: null,
          messages: null,
        },
      });
    }
  },
}));

export default useServerStore;
