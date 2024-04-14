import { create } from 'zustand';

const useSessionConnectedStore = create(set => ({
    sessionConnected: false,
    setSessionConnected: () => set(state => ({ sessionConnected: state}))
}))

const useSignalDeviceStore = create(set => ({
    camera: false,
    setCamera: () => set(state => ({camera: state})),
    mice: true,
    setMice: () => set(state => ({mice: state})),
}))


export default { useSessionConnectedStore, useSignalDeviceStore } ;