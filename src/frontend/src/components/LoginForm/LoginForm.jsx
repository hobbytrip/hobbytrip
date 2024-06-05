import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import s from "./LoginForm.module.css";
import Login from "../../hooks/User/login";
import useUserStore from "../../actions/useUserStore";
import { axiosInstance } from "../../utils/axiosInstance";
import API from "../../utils/API/TEST_API";

function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const { userId } = useUserStore();

  const handleInput = (e) => {
    const { name, value } = e.target;
    if (name === "email") setEmail(value);
    if (name === "password") setPassword(value);
  };

  const postUserIdToCommunity = async () => {
    try {
      const response = await axiosInstance.post(API.COMM_SIGNUP, {
        originalId: userId,
      });

      console.error("POST request to /community/user successful:", response);
    } catch (error) {
      console.error("Error posting userId to /community/user:", error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      await Login(email, password);
      await postUserIdToCommunity();

      navigate("/main");
    } catch (e) {
      setError("로그인 실패: " + e.message);
      console.error(e);
    }
  };

  const moveToSignUp = () => {
    navigate("/register");
  };

  return (
    <div className={s.container}>
      <form onSubmit={handleSubmit} className={s.form}>
        <h4 className={s.list}>
          이메일<a>*</a>
        </h4>
        <input
          className={s.input}
          type="email"
          id="email"
          name="email"
          value={email}
          onChange={handleInput}
          required
        />
        <h4 className={s.list}>
          비밀번호<a>*</a>
        </h4>
        <input
          className={s.input}
          type="password"
          id="password"
          name="password"
          value={password}
          onChange={handleInput}
          required
        />
        <button type="submit" className={s.loginBtn}>
          로그인
        </button>
        {error && (
          <div className={s.errorMsg}>
            <h4>{error}</h4>
          </div>
        )}
        <button type="button" onClick={moveToSignUp} className={s.helpBtn}>
          가입하기
        </button>
      </form>
    </div>
  );
}

export default LoginForm;
