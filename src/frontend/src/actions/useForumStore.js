import React from "react";
import create from "zustand";

const useForumStore = create((set) => ({
  forumLists: {},
  typingUsers: [],
  //메세지 추가
  addMessage: (serverId, forumId, message) =>
    set((state) => {
      const newForumLists = { ...state.forumLists };

      if (!newForumLists[serverId]) {
        newForumLists[serverId] = {};
      }

      if (!newForumLists[serverId][forumId]) {
        newForumLists[serverId][forumId] = [];
      }

      newForumLists[serverId][forumId].push(message);

      return { forumLists: newForumLists };
    }),

  setTypingUsers: (users) => set({ typingUsers: users }),
  // 메시지 수정
  modifyMessage: (serverId, forumId, messageId, newContent) =>
    set((state) => {
      const newForumLists = { ...state.forumLists };

      if (newForumLists[serverId][forumId]) {
        newForumLists[serverId][forumId] = newForumLists[serverId][forumId].map(
          (msg) =>
            msg.id === messageId ? { ...msg, content: newContent } : msg
        );
      }

      return { forumLists: newForumLists };
    }),

  // 메시지 삭제
  deleteMessage: (serverId, forumId, messageId) =>
    set((state) => {
      const newForumLists = { ...state.forumLists };

      if (newForumLists[serverId][forumId]) {
        newForumLists[serverId][forumId] = newForumLists[serverId][
          forumId
        ].filter((msg) => msg.id !== messageId);
      }

      return { forumLists: newForumLists };
    }),
  //forumId에 맞는 메세지 가져오기
  getMessages: (serverId, forumId) =>
    set((state) => {
      return state.forumLists[serverId]?.[forumId] || [];
    }),
}));
