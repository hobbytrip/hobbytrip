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
