import create from "zustand";

const useForumStore = create((set) => ({
  forumLists: {},
  setForumList: (channelId, forums) =>
    set((state) => ({
      forumLists: {
        ...state.forumLists,
        [channelId]: forums,
      },
    })),
  addForum: (channelId, forum) =>
    set((state) => ({
      forumLists: {
        ...state.forumLists,
        [channelId]: [forum, ...(state.forumLists[channelId] || [])],
      },
    })),

  modifyForum: (channelId, forumId, newTitle, newContent) =>
    set((state) => ({
      forumLists: {
        ...state.forumLists,
        [channelId]: state.forumLists[channelId].map((forum) =>
          forum.forumId === forumId
            ? { ...forum, title: newTitle, content: newContent }
            : forum
        ),
      },
    })),
  removeForum: (channelId, forumId) =>
    set((state) => ({
      forumLists: {
        ...state.forumLists,
        [channelId]: (state.forumLists[channelId] || []).filter(
          (forum) => forum.forumId !== forumId
        ),
      },
    })),
  resetForumList: (channelId) =>
    set((state) => ({
      forumLists: {
        ...state.forumLists,
        [channelId]: [],
      },
    })),
}));

export default useForumStore;
