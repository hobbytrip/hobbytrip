import create from "zustand";
const useDmHistoryStore = create((set) => ({
  dmHistoryList: [],

  setDmHistoryList: (newDmHistoryList) =>
    set({ dmHistoryList: newDmHistoryList }),

  addDmHistory: (newDmHistory) =>
    set((state) => ({
      dmHistoryList: [...state.dmHistoryList, newDmHistory],
    })),
}));

export default useDmHistoryStore;
