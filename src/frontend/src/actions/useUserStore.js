import create from "zustand";

const useUserStore = create((set) => ({
  user: null,
  error: null,
  login: async (email, password) => {
    try {
      const response = await axios.post("/user/login", { email, password });
      set({ user: response.data });
    } catch (error) {
      set({ error: "회원정보를 확인해주세요." });
    }
  },
  logout: () => set({ user: null }),
}));

export default useUserStore;
