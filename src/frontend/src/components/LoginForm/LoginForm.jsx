import React, { useState } from "react";
import useUserStore from "../../actions/useUserStore";
import s from "./LoginForm.module.css";

function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const login = useUserStore((state) => state.login);
  const error = useUserStore((state) => state.error);

  const handleInput = (e) => {
    const { name, value } = e.target;
    if (name == "email") setEmail(value);
    if (name == "password") setPassword(value);
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    login(email, password);
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
        <button type="submit" className={s.helpBtn}>
          비밀번호를 잊으셨나요?
        </button>
        <button type="submit" onClick={handleSubmit} className={s.loginBtn}>
          로그인
        </button>
        <div className={s.errorMsg}>{error && <h4>{error}</h4>}</div>
        <button type="submit" className={s.helpBtn}>
          가입하기
        </button>
      </form>
    </div>
  );
}
export default LoginForm;
