import { create } from 'zustand';

const useSSEStore = create((set) => ({
  serverSource: null,
  dmSource: null,

  setServerSource: (source) => set({ serverSource: source }),
  setDmSource: (source) => set({ dmSource: source }),
}));

export default useSSEStore;
