import axios from "axios";
import reIssueToken from "./Auth/reissueToken";

export const axiosInstance = axios.create({
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

axiosInstance.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    //401 unauthorized인 경우
    if (error.response && error.response.status === 401) {
      if (!originalRequest._retry) {
        originalRequest._retry = true;
        try {
          const newAccessToken = await reIssueToken();
          axios.defaults.headers.common["Authorization"] =
            `Bearer ${newAccessToken}`;
          originalRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;
          return axiosInstance(originalRequest);
        } catch (err) {
          return Promise.reject(err);
        }
      }
    }

    if (!error.response) {
      console.error("Network/Server error");
      return Promise.reject(new Error("Network/Server error"));
    }

    return Promise.reject(error);
  }
);
