import React, { useState } from "react";
import useStore from "./store";

const SignUpForm = () => {
  const { userData, setUserData } = useStore();
  const [form, setForm] = useState(userData);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prevForm) => ({ ...prevForm, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      await setUserData(form);
      alert("회원가입 성공!");
    } catch (error) {
      setError(error.response ? error.response.data.message : error.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        이메일:
        <input
          type="email"
          name="email"
          value={form.email}
          onChange={handleChange}
        />
      </label>
      <label>
        사용자명:
        <input
          type="text"
          name="username"
          value={form.username}
          onChange={handleChange}
        />
      </label>
      <label>
        별명:
        <input
          type="text"
          name="nickname"
          value={form.nickname}
          onChange={handleChange}
        />
      </label>
      <label>
        비밀번호:
        <input
          type="password"
          name="password"
          value={form.password}
          onChange={handleChange}
        />
      </label>
      <button type="submit" disabled={isLoading}>
        회원가입
      </button>
      {error && <p>오류: {error}</p>}
    </form>
  );
};

export default SignUpForm;
