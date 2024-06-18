import { axiosInstance } from "../../utils/axiosInstance";
import API from "../../utils/API/API";
import useAuthStore from "../../actions/useAuthStore";
import useUserStore from "../../actions/useUserStore";

const Login = async (email, password) => {
  const { setTokens } = useAuthStore.getState();
  const { setUserInfo } = useUserStore.getState();

  const getUser = async () => {
    try {
      const response = await axiosInstance.get(API.GET_USER_PROFLIE);
      setUserInfo(response.data);
      console.log("유저 정보 get", response.data);
    } catch (err) {
      console.error("유저 프로필 정보 GET 실패", err);
    }
  };

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
