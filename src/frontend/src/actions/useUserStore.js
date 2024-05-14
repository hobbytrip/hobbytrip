import create from "zustand";
import axios from "../utils/instance";

const useUserStore = create((set) => ({
  user: null,
  setUserInfo: (userInfo) => set({ user: userInfo }),
  updateUserInfo: async (endpoint, updates) => {
    try {
      const response = await axios.patch(`/user/profile/${endpoint}`, updates);
      if (response.status == 200) {
        set((state) => ({
          user: { ...state.user, ...updates },
        }));
      } else {
        console.log("Failed to update user data on the server");
      }
    } catch (error) {
      console.log("Error updating user data:", error);
    }
  },
  getUserInfo: async () => {
    try {
      const response = await axios.get(`/user/profile`);
      if (response.status === 200) {
        const userData = response.data;
        set({ user: userData });
      } else {
        console.log("Failed to fetch user data");
      }
    } catch (error) {
      console.log("Error fetching user data:", error);
    }
  },
  //   //유저 커뮤니티 회원가입
  postUserIdToCommunity: async () => {
    try {
      const { originalId } = useUserStore.getState().user;
      const response = await axios.post("/community/user", { originalId });
      console.log("POST request to /community/user successful:", response);
    } catch (error) {
      console.error("Error posting userId to /community/user:", error);
    }
  },
}));

export default useUserStore;
