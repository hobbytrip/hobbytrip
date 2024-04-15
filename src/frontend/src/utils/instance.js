import React, { useState, useEffect } from "react";
import axios from "axios";
import { useCookies } from "react-cookie";

function useAxios() {
  const [cookies] = useCookies(["accessToken"]);
  const [axiosInstance, setAxiosInstance] = useState(() =>
    axios.create({
      baseURL: "http://localhost:3001",
    })
  );

  useEffect(() => {
    const instance = axios.create({
      baseURL: "http://localhost:3001",
    });

    instance.interceptors.request.use(
      (config) => {
        const accessToken = cookies.accessToken;
        if (accessToken) {
          config.headers["Authorization"] = `Bearer ${accessToken}`;
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );

    setAxiosInstance(instance);
  }, [cookies.accessToken]);

  return axiosInstance;
}

export default useAxios;
