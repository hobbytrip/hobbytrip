import useAuthStore from "../../actions/useAuthStore";
import { axiosInstance } from "../../utils/axiosInstance";

const Login = async (email, password) => {
  const setTokens = useAuthStore.getState().setTokens;
  try {
    const response = await axiosInstance.post("/user/login", {
      email,
      password,
    });

    if (response.data) {
      console.error(response.data.data);
      const { accessToken, refreshToken } = response.data.data;
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);

      setTokens(accessToken, refreshToken);
    }
  } catch (error) {
    console.error("로그인 실패", error);
    throw error;
  }
};

export default Login;
