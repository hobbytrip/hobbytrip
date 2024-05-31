import { create } from "zustand";

const useAuthStore = create((set) => ({
  accessToken: null,
  refreshToken: null,
  setTokens: (accessToken, refreshToken) =>
    set({ accessToken: accessToken, refreshToken: refreshToken }),
}));

export default useAuthStore;
