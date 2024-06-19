import { axiosInstance } from "../../utils/axiosInstance";
import API from "../../utils/API/API";
import useAuthStore from "../../actions/useAuthStore";
import getUser from "../User/getUser";

const Login = async (email, password) => {
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
      await getUser();
    }
  } catch (error) {
    console.error("login.js 로그인 실패", error);
    throw error;
  }
};

export default Login;
