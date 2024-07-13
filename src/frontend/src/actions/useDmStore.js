import create from "zustand";

const useDmStore = create((set) => ({
  dmLists: {},
  typingDmUsers: [],
  setDmList: (roomId, dmList) =>
    set((state) => ({
      dmLists: {
        ...state.dmLists,
        [roomId]: dmList,
      },
    })),
  getDmList: (roomId) => {
    const dmList = useDmStore.getState().dmLists[roomId];
    return dmList ? dmList : [];
  },
  //메세지 추가
  addDmMessage: (roomId, message) =>
    set((state) => ({
      dmLists: {
        ...state.dmLists,
        [roomId]: [...state.dmLists[roomId], message],
      },
    })),

  setDmTypingUsers: (users) => set({ typingDmUsers: users }),
  modifyDmMessage: (roomId, messageId, newContent) =>
    set((state) => ({
      dmLists: {
        ...state.dmLists,
        [roomId]: state.dmLists[roomId].map((message) =>
          message.id === messageId ? { ...message, text: newContent } : message
        ),
      },
    })),
  deleteDmMessage: (roomId, messageId) =>
    set((state) => ({
      dmLists: {
        ...state.dmLists,
        [roomId]: state.dmLists[roomId].filter(
          (message) => message.id !== messageId
        ),
      },
    })),
}));
export default useDmStore;
