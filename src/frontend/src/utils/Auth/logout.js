import API from "../../utils/API/API";
import useAuthStore from "../../actions/useAuthStore";
import useUserStore from "../../actions/useUserStore";
import { axiosInstance } from "../axiosInstance";

const Logout = async (navigate) => {
  const { accessToken, refreshToken, clearTokens } = useAuthStore.getState();
  const { clearUserInfo } = useUserStore.getState();
  try {
    const response = await axiosInstance.post(API.LOG_OUT, {
      accessToken: accessToken,
      refreshToken: refreshToken,
    });
    if (response.data) {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      clearTokens();
      clearUserInfo();
      navigate("/login");
    }
  } catch (error) {
    console.error("로그아웃 실패", error);
    throw error;
  }
};

export default Logout;
