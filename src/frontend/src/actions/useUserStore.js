import { create } from "zustand";
import { axiosInstance } from "../utils/axiosInstance";
import { persist } from "zustand/middleware";

const useUserStore = create(
  persist(
    (set) => ({
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
    }),
    {
      name: "user-store",
      getStorage: () => localStorage,
    }
  )
);

export default useUserStore;
