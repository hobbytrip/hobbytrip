import { create } from "zustand";

const useAuthStore = create((set) => ({
  accesstoken: null,
  refreshtoken: null,
  setTokens: (accesstoken, refreshtoken) =>
    set({ accesstoken: accesstoken, refreshtoken: refreshtoken }),
}));

export default useAuthStore;
