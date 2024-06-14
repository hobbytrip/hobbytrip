import create from "zustand";
import { axiosInstance } from "../utils/axiosInstance";
import axios from "axios";
import API from "../utils/API/API";
import useAuthStore from "./useAuthStore";

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
      console.log("대기 중인 친구 목록:", response.data.data);
    } catch (error) {
      console.error("대기 중인 친구 조회 실패: ", error);
    }
  },
  acceptFriendRequest: async (friendshipId) => {
    try {
      await axiosInstance.post(API.ACCEPT_FRIEND_REQUEST(friendshipId));
      set((state) => ({
        waitingFriends: state.waitingFriends.filter(
          (friend) => friend.friendshipId !== friendshipId
        ),
      }));
      set((state) => ({
        friends: [
          ...state.friends,
          state.waitingFriends.find(
            (friend) => friend.friendshipId === friendshipId
          ),
        ],
      }));
    } catch (error) {
      console.error("친구 요청 수락 실패: ", error);
    }
  },
  refuseFriendRequest: async (friendshipId) => {
    const { accessToken } = useAuthStore();
    try {
      await axios.delete(API.REFUSE_FRIEND_REQUEST(friendshipId), {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      set((state) => ({
        waitingFriends: state.waitingFriends.filter(
          (friend) => friend.friendshipId !== friendshipId
        ),
      }));
    } catch (error) {
      console.error("친구 요청 거절 실패: ", error);
    }
  },
}));

export default useFriendStore;
