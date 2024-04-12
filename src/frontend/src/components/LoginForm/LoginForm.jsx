import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import userLoginStore from "../../actions/useLoginStore";
import s from "./LoginForm.module.css";

function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const login = userLoginStore((state) => state.login);
  const error = userLoginStore((state) => state.error);
  const navigate = useNavigate();

  const handleInput = (e) => {
    const { name, value } = e.target;
    if (name === "email") setEmail(value);
    if (name === "password") setPassword(value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await login(email, password);
    if (!error) {
      navigate("/home");
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
