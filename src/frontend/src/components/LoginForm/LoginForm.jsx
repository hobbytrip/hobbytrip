import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import s from "./LoginForm.module.css";
import Login from "../../hooks/User/login";

function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleInput = (e) => {
    const { name, value } = e.target;
    if (name === "email") setEmail(value);
    if (name === "password") setPassword(value);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      await Login(email, password); //스토어 login 함수 사용
      navigate("/main"); // 로그인 성공 시 메인 페이지로 리다이렉트
    } catch (e) {
      setError("로그인 실패: " + e.message);
    }
  };

  const moveToSignUp = () => {
    navigate("/register"); // 회원가입 페이지로 이동
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
        <button type="submit" onClick={handleSubmit} className={s.loginBtn}>
          로그인
        </button>
        <div className={s.errorMsg}>{error && <h4>{error}</h4>}</div>
        <button type="submit" onClick={moveToSignUp} className={s.helpBtn}>
          가입하기
        </button>
      </form>
    </div>
  );
}
export default LoginForm;
