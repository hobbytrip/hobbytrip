import { create } from 'zustand';

const usePlanetsStore = create((set) => ({
  servers: [],
  setServers: (servers) => set({ servers }),
  addServer: (server) => set((state) => ({ servers: [...state.servers, server] })),
  updateServer: (updatedServer) => set((state) => ({
    servers: state.servers.map(server =>
      server.serverId === updatedServer.serverId ? updatedServer : server
    )
  }))
}));

export default usePlanetsStore;
