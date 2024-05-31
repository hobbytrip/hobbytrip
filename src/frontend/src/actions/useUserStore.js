import { create } from "zustand";
import axios from "../utils/instance";
import API from "../utils/API/TEST_API";

const useUserStore = create((set) => ({
  user: null,
  setUserInfo: (userInfo) => set({ user: userInfo }),
  getUserInfo: async () => {
    try {
      const response = await axios.get(API.GET_USER_PROFLIE);
      if (response.status === 200) {
        const userData = response.data;
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
      if (response.status == 200) {
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
  //   //유저 커뮤니티 회원가입
  postUserIdToCommunity: async () => {
    try {
      const { originalId } = useUserStore.getState().user;
      const response = await axios.post(API.COMM_SIGNUP, { originalId });
      console.log("POST request to /community/user successful:", response);
    } catch (error) {
      console.error("Error posting userId to /community/user:", error);
    }
  },
}));

export default useUserStore;
