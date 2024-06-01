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
    try {
      const response = await axios.get(`${SERVER_URL}/${serverId}/${userId}`);
      const responseData = response.data.data;

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
