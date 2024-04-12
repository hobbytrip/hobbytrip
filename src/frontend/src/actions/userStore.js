import create from "zustand";
import axios from "axios";

const userStore = create((set) => ({
  userData: {},
  setUserData: async (userData) => {
    try {
      const response = await axios.post(
        "http://localhost:3001/users",
        userData
      );
      set({ userData: response.data.data });
      console.log("회원가입 성공:", response.data.data);
      return response.data.data;
    } catch (error) {
      console.error(
        "회원가입 실패:",
        error.response ? error.response.data.data : error.message
      );
      throw error;
    }
  },
}));

export default userStore;
