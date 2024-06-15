import create from "zustand";

const useDmStore = create((set) => ({
  dmLists: {},
  typingDmUsers: [],
  setDmList: (roomId, messages) =>
    set((state) => {
      const newDmLists = { ...state.dmLists };

      if (!newDmLists[roomId]) {
        newDmLists[roomId] = {};
      }
      newDmLists[roomId] = messages;
      return { dmLists: newDmLists };
    }),

  //메세지 추가
  addDmMessage: (roomId, message) =>
    set((state) => {
      const newDmLists = { ...state.dmLists };
      if (!newDmLists[roomId]) {
        newDmLists[roomId] = [];
      }
      newDmLists[roomId].push(message);
      return { dmLists: newDmLists };
    }),

  setDmTypingUsers: (users) => set({ typingDmUsers: users }),
  modifyDmMessage: (roomId, messageId, newContent) => {
    set((state) => {
      const newDmLists = { ...state.dmLists };

      if (newDmLists[roomId]) {
        newDmLists[roomId] = newDmLists[forumId].map((msg) =>
          msg.id === messageId ? { ...msg, content: newContent } : msg
        );
      }
      return { dmLists: newDmLists };
    });
  },
  deleteDmMessage: (roomId, messageId) =>
    set((state) => {
      const newDmLists = { ...state.dmLists };
      if (newDmLists[roomId]) {
        newDmLists[roomId] = newDmLists[roomId].filter(
          (msg) => msg.id !== messageId
        );
      }
      return { dmLists: newDmLists };
    }),
}));
export default useDmStore;
