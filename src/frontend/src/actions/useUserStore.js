import create from "zustand";
import axios from "../utils/instance";

const useUserStore = create((set) => ({
  user: null,
  setUserInfo: (userInfo) => set({ user: userInfo }),
  updateUserInfo: async (endpoint, updates) => {
    try {
      const response = await axios.patch(`/user/profile/${endpoint}`, updates);
      if (response.status === 200) {
        set((state) => ({
          user: { ...state.user, ...updates },
        }));
      } else {
        console.error("Failed to update user data on the server");
      }
    } catch (error) {
      console.error("Error updating user data:", error);
    }
  },
}));

export default useUserStore;
