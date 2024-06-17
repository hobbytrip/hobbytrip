import { create } from 'zustand';

const usePlanetsStore = create((set) => ({
  servers: null,
  setServers: (servers) => set({ servers }),
}));

export default usePlanetsStore;