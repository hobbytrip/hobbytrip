import axios from "axios";
import useAuthStore from "../actions/useAuthStore";

export const axiosInstance = axios.create({
  baseURL: "https://fittrip.site/api",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

axiosInstance.interceptors.request.use(
  (config) => {
    const accessToken = useAuthStore.getState().accessToken;
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    console.log("axios config : ", config);
    return config;
  },
  (error) => {
    console.log("axios config : ", error);
    return Promise.reject(error);
  }
);
