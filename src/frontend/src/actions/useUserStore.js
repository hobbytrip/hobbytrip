import { create } from "zustand";
import { axiosInstance } from "../utils/axiosInstance";
import API from "../utils/API/TEST_API";

const useUserStore = create((set) => ({
  user: null,
  setUserInfo: (userInfo) => set({ user: userInfo }),
  getUserInfo: async () => {
    try {
      const response = await axiosInstance.get(API.GET_USER_PROFLIE);
      if (response.data) {
        const userData = response.data;
        set({ user: userData });
      } else {
        console.log("Failed to fetch user data");
      }
    } catch (error) {
      console.log("Error fetching user data:", error);
    }
  },
  updateUserInfo: async (endpoint, updates) => {
    try {
      const response = await axiosInstance.patch(
        `/user/profile/${endpoint}`,
        updates
      );
      if (response.data) {
        set((state) => ({
          user: { ...state.user, ...updates },
        }));
      } else {
        console.error("Failed to update user data");
      }
    } catch (error) {
      console.log("Error updating user data:", error);
    }
  },
  postUserIdToCommunity: async () => {
    try {
      const { originalId } = useUserStore.getState().user;
      const response = await axiosInstance.post(API.COMM_SIGNUP, {
        originalId,
      });
      console.log("POST request to /community/user successful:", response);
    } catch (error) {
      console.error("Error posting userId to /community/user:", error);
    }
  },
}));

export default useUserStore;
