import axios from "axios";

export const axiosInstance = axios.create({
  baseURL: "https://fittrip.site/api",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});
