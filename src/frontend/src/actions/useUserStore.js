import { create } from "zustand";
import { axiosInstance } from "../utils/axiosInstance";

const useUserStore = create((set) => ({
  userId: "",
  email: "",
  nickname: "",
  username: "",
  birthdate: "",
  notificationEnabled: false,
  createAt: "",
  setUserInfo: (userInfo) => set(userInfo),
  updateUserInfo: async (endpoint, updates) => {
    try {
      const response = await axiosInstance.patch(
        `/user/profile/${endpoint}`,
        updates
      );
      if (response.data) {
        set((state) => ({
          ...state,
          ...updates,
        }));
      } else {
        console.error("Failed to update user data");
      }
    } catch (error) {
      console.log("Error updating user data:", error);
    }
  },
}));

export default useUserStore;
