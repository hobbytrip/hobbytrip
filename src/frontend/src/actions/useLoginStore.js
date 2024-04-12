import create from "zustand";
import axios from "axios";

const useLoginStore = create((set) => ({
  user: null,
  error: null,
  login: async (email, password) => {
    try {
      const response = await axios.post("http://localhost:3001/login", {
        email,
        password,
      });
      if (response.status === 200) {
        set({ user: response.data, error: null });
        console.log("로그인 성공");
      } else {
        throw new Error("로그인 실패");
      }
    } catch (error) {
      console.error(error);
      set({ error: "회원정보를 확인해주세요.", user: null });
    }
  },
  logout: () => set({ user: null }),
}));

export default useLoginStore;
