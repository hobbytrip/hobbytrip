import create from "zustand";
import { axiosInstance } from "../utils/axiosInstance";
import API from "../utils/API/API";

const useFriendStore = create((set) => ({
  friends: [],
  waitingFriends: [],
  fetchFriends: async () => {
    try {
      const response = await axiosInstance.get(API.GET_FRIENDS);
      set({ friends: response.data.data || [] });
      console.log("친구 목록:", response.data.data);
    } catch (error) {
      console.error("친구 목록 조회 실패: ", error);
    }
  },
  fetchWaitingFriends: async () => {
    try {
      const response = await axiosInstance.get(API.WAITING_FRIEND);
      set({ waitingFriends: response.data.data || [] });
      console.error("대기 중인 친구 목록:", response.data.data);
    } catch (error) {
      console.error("대기 중인 친구 조회 실패: ", error);
    }
  },
}));

export default useFriendStore;
