import { create } from "zustand";
import API from "../utils/API/API";
import { axiosInstance } from "../utils/axiosInstance";

const useServerStore = create((set, get) => ({
  serverData: {
    serverInfo: null,
    serverCategories: null,
    serverChannels: null,
    serverUserInfos: null,
    userStatus: null,
    messages: null,
  },
  setServerData: (newServerData) => {
    set({ serverData: newServerData });
  },
  setServerInfo: (newServerInfo) =>
    set((state) => ({
      serverData: {
        ...state.serverData,
        serverInfo: newServerInfo,
      },
    })),
  fetchServerData: async (serverId, userId) => {
    try {
      const res = await axiosInstance.get(API.GET_SERVER(serverId, userId));
      console.log("Fetched Data", res);
      const resData = res.data.data;
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
        console.log("Set data", get().serverData);
      } else {
        console.error("Error: Data not found in response", res);
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
