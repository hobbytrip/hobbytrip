import create from "zustand";

const useUserStore = create((set) => ({
  userData: {
    userInfo: null,
  },

  setUserInfo: (info) =>
    set(() => ({
      userInfo: info,
    })),
}));

export default useUserStore;
