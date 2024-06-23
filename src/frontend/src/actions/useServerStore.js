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
  setServerData: (data) => {
    set({
      serverData: {
        serverInfo: data.server,
        serverCategories: data.categories,
        serverChannels: data.channels,
        serverUserInfos: data.serverUserInfos,
        userStatus: data.usersState,
        messages: data.messages,
      },
    });
  },
  setServerInfo: (newServerInfo) =>
    set((state) => ({
      serverData: {
        ...state.serverData,
        serverInfo: newServerInfo,
      },
    })),
}));

export default useServerStore;
