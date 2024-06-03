import { create } from 'zustand';
import axios from 'axios';
import API from '../utils/API/API';

const SERVER_URL = API.COMM_SERVER

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
    console.log(serverId, userId);
    try {
      const res = await axiosInstance.get(API.GET_SERVER(serverId, userId));
      console.log('fetching')
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
        console.log('fetched')
      } else {
        console.error("Error: Data not found in res", res);
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
      set({
        serverData: {
          serverInfo: responseData.server,
          serverCategories: responseData.categories,
          serverChannels: responseData.channels,
          userStatus: responseData.userOnOff,
          messages: responseData.messages,
        },
      });
    } catch (error) {
      console.error("Error fetching server data:", error);
    }
  },
}));

export default useServerStore;
