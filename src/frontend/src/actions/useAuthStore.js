import create from "zustand";
import { useCookies } from "react-cookie";

const useAuthStore = create((set, get) => {
  const [cookies, setCookie, removeCookie] = useCookies([
    "accessToken",
    "refreshToken",
  ]);

  return {
    accessToken: cookies.accessToken,
    refreshToken: cookies.refreshToken,

    login: async (email, password) => {
      try {
        const response = await axios.post("/login", {
          email,
          password,
        });
        const { accessToken, refreshToken } = response.data;
        if (accessToken && refreshToken) {
          //path: / : 도메인의 모든 경로에 대해 쿠키 유효
          setCookie("accessToken", accessToken, {
            path: "/",
            secure: true,
            sameSite: "strict",
          });
          setCookie("refreshToken", refreshToken, {
            path: "/",
            secure: true,
            sameSite: "strict",
          });
          set({ accessToken, refreshToken });
          console.log("로그인 성공");
        }
      } catch (error) {
        console.error("로그인 실패", error);
      }
    },

    logout: async (accessToken, refreshToken) => {
      const response = await axios.post("/logout", {
        accessToken,
        refreshToken,
      });
      if (response.data == true) {
        removeCookie("accessToken", { path: "/" });
        removeCookie("refreshToken", { path: "/" });
        set({ accessToken: undefined, refreshToken: undefined });
        console.log("로그아웃 완료");
      }
    },
  };
});

export default useAuthStore;
