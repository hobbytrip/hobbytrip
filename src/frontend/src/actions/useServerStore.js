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
  setServerData: (newData) => {
    set(() => ({
      serverData: {
        serverInfo: newData.server,
        serverCategories: newData.categories || null,
        serverChannels: newData.channels || null,
        serverUserInfos: newData.serverUserInfos || null,
        userStatus: newData.usersState || null,
        messages: newData.messages || null,
      },
    }));
  },
  setServerInfo: (newServerInfo) => {
    set((state) => ({
      serverData: {
        ...state.serverData,
        serverInfo: newServerInfo,
      },
    }));
  },
  setServerCategories: (newServerCategories) => {
    set((state) => ({
      serverData: {
        ...state.serverData,
        serverCategories: newServerCategories,
      },
    }));
  },
  setServerChannels: (newServerChannels) => {
    set((state) => ({
      serverData: {
        ...state.serverData,
        serverChannels: newServerChannels,
      },
    }));
  },
  setServerUserInfos: (newServerUserInfos) => {
    set((state) => ({
      serverData: {
        ...state.serverData,
        serverUserInfos: newServerUserInfos,
      },
    }));
  },
  setUserStatus: (newUserStatus) => {
    set((state) => ({
      serverData: {
        ...state.serverData,
        serverUserStatus: newUserStatus,
      },
    }));
  },
  setUserStatus: (newUserStatus) => {
    set((state) => ({
      serverData: {
        ...state.serverData,
        serverUserStatus: newUserStatus,
      },
    }));
  },
  setMessages: (newMessages) => {
    set((state) => ({
      serverData: {
        ...state.serverData,
        serverMessages: newMessages,
      },
    }));
  },
}));

export default useServerStore;
