import create from "zustand";

const useDmHistoryStore = create((set) => ({
  dmHistoryList: [],
  setDmHistoryList: (newDmHistoryList) =>
    set({ dmHistoryList: newDmHistoryList }),
}));

export default useDmHistoryStore;
