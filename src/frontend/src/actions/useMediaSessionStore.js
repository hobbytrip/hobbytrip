import { create } from 'zustand';
import { OpenVidu, Publisher } from 'openvidu-browser';
import axios from 'axios';

// const URL = 'ws://localhost:4443';

const useMediaCallStore = create((set) => ({
  sessionList: [],
  newsession: null,
  publisher: null,
  subscribers: [],

  checkSessionExist: async (sessionId) => {
    try{
      const sessions = (await axios.get(`${URL}/api/sessions`)).data
      return sessions.find(session => session.sessionId === sessionId);;
    } catch (err){
      console.error('Error creating session:', err);
    }
  },
  
  createSession: async () => {
    try {
      const res = await axios.post(URL + 'openvidu/api/sessions', {
        headers: { 'Content-Type': 'application/json' },
      });
      
      const newSessionData = {
        session: res.data,
        users: []
      };
      
      set((state) => ({ sessionList: [...state.sessionList, newSessionData] }));
      
    } catch (error) {
      console.error('Error creating session:', error);
      return null;
    }
  },

  joinSession: async () => {
    const OV = new OpenVidu();
    const newsession = OV.initSession();

    newsession.on('streamCreated', (event) => {
      const subscriber = newsession.subscribe(event.stream, undefined);
      set((state) => ({ subscribers: [...state.subscribers, subscriber] }));
    });

    newsession.on('streamDestroyed', (event) => {
      const streamId = event.stream.streamId;
      set((state) => ({
        subscribers: state.subscribers.filter((subscriber) => subscriber.stream.streamId !== streamId),
      }));
    });

    

    set({ newsession });

    await getState().setUsers();

  },

  setUsers: async () => {
    // publish 관리
    try {
      const { newsession } = useMediaCallStore.getState();
      const publisher = new Publisher();
      await newsession.connect();
      const media = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
      publisher.resetVideoElement();
      publisher.addVideoElement('publisher-video');
      publisher.addAudioElement('publisher-audio');
      publisher.publish(media);
      set({ publisher, isConnected: true });
    } catch (err) {
      console.error('Error setting publisher:', err);
    }

    // subscriber 관리
    try {
      await newsession.connect();

      const existingPublishers = newsession.remoteStreams;
      existingPublishers.forEach(stream => {
        const subscriber = newsession.subscribe(stream, undefined);
        set((state) => ({ subscribers: [...state.subscribers, subscriber] }));
      });
      
      await useMediaCallStore.getState().setUsers();
    } catch (err) {
      console.error('Error joining session:', err);
    }
  },
}));

export default useMediaCallStore;