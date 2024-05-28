import { create } from 'zustand';

const useStore = create((set) => ({
  userRooms: [], // 유저가 속한 방 목록
  unreadRooms: [], // 읽지 않은 알림이 있는 방 목록

  addNotification: (roomId) =>
    set((state) => ({
      unreadRooms: [...state.unreadRooms, roomId],
    })),

  markAsRead: (roomId) =>
    set((state) => ({
      unreadRooms: state.unreadRooms.filter((id) => id !== roomId),
    })),

  setUserRooms: (rooms) =>
    set(() => ({
      userRooms: rooms,
    })),
}));

export default useStore;