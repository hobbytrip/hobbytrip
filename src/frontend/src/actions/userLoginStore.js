import create from "zustand";

const userLoginStore = create((set) => ({
  user: null,
  error: null,
  login: async (email, password) => {
    try {
      const response = await axios.post("http://localhost:3001/login", {
        email,
        password,
      });
      set({ user: response.data });
      console.log("로그인 성공");
    } catch (error) {
      set({ error: "회원정보를 확인해주세요." });
    }
  },
  logout: () => set({ user: null }),
}));

export default userLoginStore;
