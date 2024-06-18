import create from "zustand";

const useDmHistoryStore = create((set) => ({
  dmHistoryList: [],

  setDmHistoryList: (newDmHistoryList) =>
    set({ dmHistoryList: newDmHistoryList }),

  addDmHistory: (newDmHistory) =>
    set((state) => {
      // 존재여부 확인
      const exists = state.dmHistoryList.some(
        (history) => history.dmId === newDmHistory.dmId
      );

      // 존재하지 않으면
      if (!exists) {
        return { dmHistoryList: [...state.dmHistoryList, newDmHistory] };
      }
      return state;
    }),
}));

export default useDmHistoryStore;
