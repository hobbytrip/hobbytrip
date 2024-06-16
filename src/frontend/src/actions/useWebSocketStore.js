import create from "zustand";
import * as StompJs from "@stomp/stompjs";
import SockJS from "sockjs-client";
import useUserStatusStore from "./useUserStatusStore";

const useWebSocketStore = create((set, get) => ({
  client: null,
  isConnected: false,

  connect: (userId) => {
    const stompClient = new StompJs.Client({
      webSocketFactory: () => new SockJS("https://fittrip.site/stomp/ws-stomp"),
      debug: (str) => console.log(str),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      connectHeaders: {
        userId: userId,
      },
    });

    stompClient.activate();
    set({ client: stompClient, isConnected: true });
    const updateUserState = useUserStatusStore.getState().updateUserState;
    updateUserState(userId, "ONLINE"); // 온라인 상태
    console.log("상태 업데이트 성공");
    console.log("소켓 클라이언트 생성 성공");

    // 클린업 함수
    return () => {
      if (stompClient.connected) {
        stompClient.deactivate();
        set({ client: null, isConnected: false });
        updateUserState(userId, "OFFLINE"); // 오프라인 상태
        console.log("WebSocket 연결 해제 및 클린업 완료");
      }
    };
  },

  disconnect: () => {
    const { client } = get();
    if (client && client.connected) {
      client.deactivate();
      set({ client: null, isConnected: false });
      const updateUserState = useUserStatusStore.getState().updateUserState;
      // const userId = client._connectHeaders.userId;
      // updateUserState(userId, "OFFLINE"); // 오프라인 상태
    }
  },
}));

export default useWebSocketStore;
