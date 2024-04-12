import { useState } from "react";
import axios from "axios"; // axios 임포트 추가
import s from "./RegForm.module.css";
import userStore from "../../actions/userStore";
import { useNavigate } from "react-router-dom";

function RegForm() {
  const { setUserData } = userStore();
  const [form, setForm] = useState({
    email: "",
    username: "",
    nickname: "",
    password: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prevForm) => ({ ...prevForm, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const response = await axios.post("http://localhost:3001/users", form);
      setUserData(response.data);
      console.log("회원가입 성공:", response.data);
      navigate("/login");
    } catch (error) {
      console.error(
        "회원가입 실패:",
        error.response ? error.response.data : error.message
      );
      setError(error.response ? error.response.data.message : error.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className={s.container}>
      <form onSubmit={handleSubmit} className={s.forms}>
        <h4 className={s.list}>
          이메일<a>*</a>
        </h4>
        <input
          className={s.box}
          type="email"
          name="email"
          value={form.email}
          onChange={handleChange}
          required
        />
        <h4 className={s.list}>
          사용자명<a>*</a>
        </h4>
        <input
          className={s.box}
          type="text"
          name="username"
          value={form.username}
          onChange={handleChange}
          required
        />
        <h4 className={s.list}>별명</h4>
        <input
          className={s.box}
          type="text"
          name="nickname"
          value={form.nickname}
          onChange={handleChange}
        />
        <h4 className={s.list}>
          비밀번호<a>*</a>
        </h4>
        <input
          className={s.box}
          type="password"
          name="password"
          value={form.password}
          onChange={handleChange}
          required
        />
        <button type="submit" className={s.signUpBtn} disabled={isLoading}>
          가입하기
        </button>
        {error && <p>오류: {error}</p>}
      </form>
    </div>
  );
}

export default RegForm;
