import { create } from 'zustand';
import axios from 'axios';

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
      // const URL = 'http://34.64.217.76:8080',
      const URL = 'http://localhost:8080';
      const response = await axios.get(`${URL}/server/${serverId}/${userId}`);
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
