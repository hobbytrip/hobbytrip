import axios from "axios";
import reIssueToken from "./Auth/reissueToken";

export const axiosInstance = axios.create({
  // baseURL: "https://fittrip.site/api",
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

    if (error.response.status === 401 || error.response.status === 403) {
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

    return Promise.reject(error);
  }
);
