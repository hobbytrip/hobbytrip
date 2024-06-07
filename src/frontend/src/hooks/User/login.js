import { axiosInstance } from "../../utils/axiosInstance";
import API from "../../utils/API/API";
import useAuthStore from "../../actions/useAuthStore";
import useWebSocketStore from "../../actions/useWebSocketStore";

const Login = async (email, password, userId) => {
  const { setTokens } = useAuthStore.getState();
  try {
    const response = await axiosInstance.post(API.LOG_IN, {
      email,
      password,
    });

    if (response.data) {
      console.error(response.data.data);
      const { accessToken, refreshToken } = response.data.data;
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);

      setTokens(accessToken, refreshToken);
      //로그인하면 소켓에 연결
      useWebSocketStore.getState().connect(userId);
    }
  } catch (error) {
    console.error("로그인 실패", error);
    throw error;
  }
};

export default Login;
