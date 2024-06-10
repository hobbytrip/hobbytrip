import { create } from "zustand";

const useChatStore = create((set) => ({
  chatLists: {},
  typingUsers: [],
  setChatList: (serverId, messages) =>
    set((state) => ({
      chatLists: {
        [serverId]: [...messages],
      },
    })),
  sendMessage: (message) => {
    set((state) => ({
      chatLists: {
        ...state.chatLists,
        [message.serverId]: [
          ...(state.chatLists[message.serverId] || []),
          message,
        ],
      },
    }));
  },
  setTypingUsers: (users) => set({ typingUsers: users }),
  modifyMessage: (serverId, messageId, newContent) =>
    set((state) => ({
      chatLists: {
        ...state.chatLists,
        [serverId]: state.chatLists[serverId].map((message) =>
          message.messageId === messageId
            ? { ...message, content: newContent }
            : message
        ),
      },
    })),
  deleteMessage: (serverId, messageId) =>
    set((state) => ({
      chatLists: {
        ...state.chatLists,
        [serverId]: state.chatLists[serverId].filter(
          (message) => message.messageId !== messageId
        ),
      },
    })),
}));

export default useChatStore;
