// import { axiosInstance } from "../axiosInstance";
import API from "../../utils/API/API";
import axios from "axios";

const Logout = async (accessToken, refreshToken, setTokens, navigate) => {
  try {
    const response = await axios.post(
      API.LOG_OUT,
      {
        accessToken,
        refreshToken,
      },
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        withCredentials: true,
      }
    );
    if (response.status === 200) {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      setTokens(null, null);
      navigate("/login");
    }
  } catch (error) {
    console.error("로그아웃 실패", error);
    throw error;
  }
};

export default Logout;
