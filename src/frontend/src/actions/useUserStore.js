import { create } from "zustand";
import axios from "axios";
import { persist } from "zustand/middleware";
import API from "../utils/API/API";

const useUserStore = create(
  persist(
    (set) => ({
      userId: "",
      email: "",
      nickname: "",
      username: "",
      birthdate: "",
      notificationEnabled: false,
      createdAt: "",
      setUserInfo: (userInfo) => set(userInfo),
      updateUserInfo: async (endpoint, updates) => {
        try {
          const response = await axios.patch(API.UPDATE_PROFILE(endpoint), {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
            updates,
          });
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
    }),
    {
      name: "user-store",
      getStorage: () => localStorage,
    }
  )
);

export default useUserStore;
