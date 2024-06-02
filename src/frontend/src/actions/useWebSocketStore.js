import create from "zustand";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";

const useWebSocketStore = create((set, get) => ({
  client: null,
  isConnected: false,
  connect: (userId) => {
    const stompClient = new StompJs.Client({
      webSocketFactory: () => new SockJS("http://localhost:7070/ws-stomp"),
      debug: (str) => console.log(str),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      connectHeaders: {
        userId: userId,
      },
    });

    stompClient.activate();
    set({ client: stompClient });
  },
  disconnect: () => {
    const { client } = get();
    if (client && client.connected) {
      client.deactivate();
      set({ client: null, isConnected: false });
    }
  },
  sendMessage: (destination, body) => {
    const { client } = get();
    if (client && client.connected) {
      client.publish({
        destination,
        body: JSON.stringify(body),
      });
    } else {
      console.log("웹소켓 연결 nono.");
    }
  },
}));

export default useWebSocketStore;
