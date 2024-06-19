import { useNavigate } from "react-router-dom";

import axios from "axios";
import useAuthStore from "../../actions/useAuthStore";
import API from "../../utils/API/API";

const Logout = async (accessToken, refreshToken) => {
  const setTokens = useAuthStore((state) => state.setTokens);
  const nav = useNavigate();
  try {
    const response = await axios.post(API.LOG_OUT, {
      accessToken: accessToken,
      refreshToken: refreshToken,
    });
    if (response.status == 200) {
      localStorage.removeItem(accessToken);
      localStorage.removeItem(refreshToken);
      setTokens(null, null);
      nav("/login");
      // alert("로그아웃 되었습니다.");
    }
  } catch (error) {
    console.error("로그아웃 실패", error);
    throw error;
  }
};
export default Logout;
