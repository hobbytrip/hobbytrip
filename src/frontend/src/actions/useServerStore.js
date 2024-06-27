import { create } from "zustand";

const useServerStore = create((set) => ({
  serverData: {
    serverInfo: null,
    serverCategories: null,
    serverChannels: null,
    serverUserInfos: null,
    userStatus: null,
    messages: null,
  },
  setServerData: (newData) => set((state) => ({
    serverData: {
      ...state.serverData,
      ...newData,
    }
  }))
}));

export default useServerStore;
