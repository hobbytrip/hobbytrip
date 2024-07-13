import { create } from "zustand";

const useUserStatusStore = create((set) => ({
  users: {},
  onlineUsers: [], // 온라인 유저 배열
  offlineUsers: [], // 오프라인 유저 배열

  setUsers: (users) =>
    set({
      users,
      onlineUsers: Object.keys(users).filter(
        (userId) => users[userId] === "ONLINE"
      ),
      offlineUsers: Object.keys(users).filter(
        (userId) => users[userId] !== "ONLINE"
      ),
    }),

  updateUserState: (userId, status) =>
    set((state) => {
      const updatedUsers = { ...state.users, [userId]: status };
      return {
        users: updatedUsers,
        onlineUsers: Object.keys(updatedUsers).filter(
          (userId) => updatedUsers[userId] === "ONLINE"
        ),
        offlineUsers: Object.keys(updatedUsers).filter(
          (userId) => updatedUsers[userId] !== "ONLINE"
        ),
      };
    }),
}));

export default useUserStatusStore;
