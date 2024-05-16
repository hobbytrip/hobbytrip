import React, { useState, useEffect } from "react";
import axios from "axios";

function useAxios() {
  const [axiosInstance, setAxiosInstance] = useState(() =>
    axios.create({
      baseURL: "http://localhost:8080",
    })
  );

  useEffect(() => {
    const instance = axios.create({
      baseURL: "http://localhost:8080",
    });

    instance.interceptors.request.use(
      (config) => {
        const accessToken = localStorage.getItem("accessToken"); //localStorage사용
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
  }, []);

  return axiosInstance;
}

export default useAxios;
