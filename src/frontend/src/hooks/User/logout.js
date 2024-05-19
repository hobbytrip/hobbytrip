import axios from "axios";
import useAuthStore from "../../actions/useAuthStore";
const Logout = async (accesstoken, refreshtoken) => {
  const setTokens = useAuthStore((state) => state.setTokens);
  try {
    const response = await axios.post("/user/logout", {
      accesstoken: accesstoken,
      refreshtoken: refreshtoken,
    });
    if (response.status == 200) {
      localStorage.removeItem(accesstoken);
      localStorage.removeItem(refreshtoken);
      setTokens(null, null);
    }
  } catch (error) {
    console.error("로그인 실패", error);
    throw error;
  }
};
export default Logout;
