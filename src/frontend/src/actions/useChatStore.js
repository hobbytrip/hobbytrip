import create from "zustand";

const useChatStore = create((set) => ({
  chatList: [],
  typingUsers: [],
  sendMessage: (message) =>
    set((state) => ({ chatList: [...state.chatList, message] })),
  setTypingUsers: (users) => set({ typingUsers: users }),
  modifyMessage: (messageId, newContent) =>
    set((state) => ({
      chatList: state.chatList.map((message) =>
        message.messageId === messageId
          ? { ...message, content: newContent }
          : message
      ),
    })),
  deleteMessage: (messageId) =>
    set((state) => ({
      chatList: state.chatList.filter(
        (message) => message.messageId !== messageId
      ),
    })),
}));

export default useChatStore;
