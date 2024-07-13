import axios from "axios";
import useAuthStore from "../../actions/useAuthStore";
import API from "../API/API";
import getUser from "../User/getUser";

const reIssueToken = async () => {
  const accessToken = localStorage.getItem("accessToken");
  const refreshToken = localStorage.getItem("refreshToken");
  const response = await axios.post(API.REISSUE, {
    accessToken,
    refreshToken,
  });
  const newAccessToken = response.data.accessToken;
  const newRefreshToken = response.data.refreshToken;
  localStorage.setItem("accessToken", newAccessToken);
  localStorage.setItem("refreshToken", newRefreshToken);
  useAuthStore.getState().setTokens(newAccessToken, newRefreshToken);
  await getUser();
  return newAccessToken;
};

export default reIssueToken;
