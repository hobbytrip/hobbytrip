// import React from "react";
import create from "zustand";

const useForumStore = create((set) => ({
  forumLists: {},
  forumTypingUsers: [],
  setForumList: (serverId, forumId, messages) =>
    set((state) => {
      const newForumLists = { ...state.forumLists };

      if (!newForumLists[serverId]) {
        newForumLists[serverId] = {};
      }

      newForumLists[serverId][forumId] = messages;

      return { forumLists: newForumLists };
    }),
  //메세지 추가
  addForumMessage: (serverId, forumId, message) =>
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

  setForumTypingUsers: (users) => set({ forumTypingUsers: users }),
  // 메시지 수정
  modifyForumMessage: (serverId, forumId, messageId, newContent) =>
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
  deleteForumMessage: (serverId, forumId, messageId) =>
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
  // getForumMessages: (serverId, forumId) => (state) => {
  //   return state.forumLists[serverId]?.[forumId] || [];
  // },
}));

export default useForumStore;
