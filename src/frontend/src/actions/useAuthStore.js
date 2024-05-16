import create from "zustand";

const useAuthStore = create((set, get) => {
  const accesstoken = localStorage.getItem("accesstoken");
  const refreshtoken = localStorage.getItem("refreshtoken");

  return {
    accesstoken,
    refreshtoken,

    login: async (email, password) => {
      try {
        const response = await axios.post("/user/login", {
          email,
          password,
        });
        const { accesstoken, refreshtoken } = response.data;
        if (accesstoken && refreshtoken) {
          localStorage.setItem("accesstoken", accesstoken);
          localStorage.setItem("refreshtoken", refreshtoken);
          set({ accesstoken, refreshtoken });
          console.log("로그인 성공");
        }
      } catch (error) {
        console.error("로그인 실패", error);
        throw error;
      }
    },

    logout: async () => {
      try {
        const response = await axios.post("/user/logout", {
          accesstoken,
          refreshtoken,
        });
        if (response.status == 200) {
          localStorage.removeItem("accesstoken");
          localStorage.removeItem("refreshtoken");
          set({ accesstoken: undefined, refreshtoken: undefined });
          console.log("로그아웃 완료");
        }
      } catch (error) {
        console.error("로그아웃 실패", error);
      }
    },
  };
});

export default useAuthStore;
