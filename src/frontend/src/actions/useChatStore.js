import create from "zustand";

const useChatStore = create((set) => ({
  //소켓으로 받은 새로운 메세지(전송, 삭제, 수정)
  MESSAGE: null,
  // 받은 메세지 저장
  setMessage: (message) => set({ MESSAGE: message }),
  //메세지 처리가 끝나면 해당 메세지 store에서 제거
  removeMessage: () => set({ MESSAGE: null }),
}));

export default useChatStore;
