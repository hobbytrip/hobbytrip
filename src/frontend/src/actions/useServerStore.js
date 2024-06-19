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
    console.log(get().serverData);  // Log the new server data after setting it
  },
  setServerInfo: (newServerInfo) => set((state) => ({
    serverData: {
      ...state.serverData,
      serverInfo: newServerInfo,
    },
  })),
  fetchServerData: async (serverId, userId) => {
    try {
      console.log('서버 정보 처음 요청 정보', serverId, userId);
      const res = await axiosInstance.get(API.GET_SERVER(serverId, userId));
      const resData = res.data.data;
      console.error('처음 받은 서버 정보', resData);
      if (resData) {
        set({ serverData: {
            serverInfo: resData.server,
            serverCategories: resData.categories,
            serverChannels: resData.channels,
            serverUserInfos: resData.serverUserInfos,
            userStatus: resData.usersState,
            messages: resData.messages,
        }});
        console.log('serverData 설정 후', get().serverData);  // Log the server data after setting it
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
        console.log(get().serverData);  // Log the server data even when setting to null
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
