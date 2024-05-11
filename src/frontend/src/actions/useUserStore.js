import create from "zustand";
import axios from "../utils/instance";

const useUserStore = create((set) => ({
  user: null,
  setUserInfo: (userInfo) => set({ user: userInfo }),
  getUserInfo: async () => {
    try {
      const response = await axios.get(`/user/profile`);
      if (response.status === 200) {
        const userData = response.data.data;
        set({ user: userData });
      } else {
        console.log("Falied to fetch user data");
      }
    } catch (error) {
      console.log("Error fetching user data:", error);
    }
  },
  updateUserInfo: async (endpoint, updates) => {
    try {
      const response = await axios.patch(`/user/profile/${endpoint}`, updates);
      if (response.success === "true") {
        set((state) => ({
          user: { ...state.user, ...updates },
        }));
      } else {
        console.error("Failed to update user data");
      }
    } catch (error) {
      console.error("Error updating user data:", error);
    }
  },
}));

export default useUserStore;
