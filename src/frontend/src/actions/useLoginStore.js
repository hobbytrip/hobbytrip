import create from "zustand";
import axios from "axios";

const useLoginStore = create((set, get) => ({
  user: null,
  error: null,
  login: async (email, password) => {
    try {
      const response = await axios.post("http://localhost:3001/login", {
        email,
        password,
      });
      if (response.status === 200) {
        // 토큰을 axios의 기본 헤더에 설정
        const { accessToken } = response.data;
        axios.defaults.headers.common["Authorization"] =
          `Bearer ${accessToken}`;

        // 사용자 데이터를 상태에 저장
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
  logout: () => {
    // 로그아웃 시 토큰 정보 제거
    delete axios.defaults.headers.common["Authorization"];
    set({ user: null });
    console.log("로그아웃 완료");
  },
}));

export default useLoginStore;
