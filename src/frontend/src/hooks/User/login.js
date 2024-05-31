import { axiosInstance } from "../../utils/axiosInstance";
import API from "../../utils/API/TEST_API";

const Login = async (email, password) => {
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
    }
  } catch (error) {
    console.error("로그인 실패", error);
    throw error;
  }
};

export default Login;
