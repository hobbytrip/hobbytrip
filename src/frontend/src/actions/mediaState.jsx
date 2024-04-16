import { create } from "zustand";

const useMediaConnectedStore = create((set) => ({
    mediaConnected: false,
    setMediaConnected: (state) => ({ mediaConnected: state})
}));

export default useMediaConnectedStore;