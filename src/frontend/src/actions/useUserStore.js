import { create } from "zustand";
import axios from "axios";
import API from "../utils/API/API";
import useAuthStore from "./useAuthStore";

const useUserStore = create((set) => ({
  USER: JSON.parse(localStorage.getItem("user")) || {},
  notificationEnabled: false,

  setUserInfo: (user) => {
    localStorage.setItem("user", JSON.stringify(user));
    set({ USER: user });
  },

  clearUserInfo: () => {
    localStorage.removeItem("user");
    set({ USER: {} });
  },

  updateUserInfo: async (endpoint, updates) => {
    try {
      const { accessToken } = useAuthStore.getState();
      const response = await axios.patch(
        API.UPDATE_PROFILE(endpoint),
        updates,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
          withCredentials: true,
        }
      );

      if (response.data) {
        const updatedUser = { ...useUserStore.getState().USER, ...updates };
        localStorage.setItem("user", JSON.stringify(updatedUser));
        set((state) => ({
          ...state,
          USER: updatedUser,
        }));
      } else {
        console.error("Failed to update user data");
      }
    } catch (error) {
      console.log("Error updating user data:", error);
    }
  },

  setNotificationEnabled: (enabled) =>
    set(() => ({ notificationEnabled: enabled })),
  clearUserInfo: () => {
    localStorage.removeItem("user");
    set({ USER: {} });
  },
}));

export default useUserStore;
