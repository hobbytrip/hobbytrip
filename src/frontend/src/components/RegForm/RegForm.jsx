import { useState } from "react";
import s from "./RegForm.module.css";
import { useNavigate } from "react-router-dom";
import useUserStore from "../../actions/useUserStore";
import NotificationBox from "../NotificationBox/NotificationBox";
import { axiosInstance } from "../../utils/axiosInstance";
import API from "../../utils/API/TEST_API";

function RegForm() {
  const setUserInfo = useUserStore((state) => state.setUserInfo);
  const { userId } = useUserStore();

  const [form, setForm] = useState({
    email: "",
    username: "",
    nickname: "",
    password: "",
    birthdate: "",
    notificationEnabled: false,
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, type, checked, value } = e.target;
    const newValue = type === "checkbox" ? checked : value;
    setForm((prevForm) => ({
      ...prevForm,
      [name]: newValue,
    }));
  };

  const moveToLogin = () => {
    navigate("/login");
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
    setIsLoading(true);
    try {
      const response = await axiosInstance.post(API.SIGN_UP, form);
      if (response.data.success) {
        setUserInfo(response.data.data);
        await postUserIdToCommunity();
        console.log("회원가입 성공:", response.data.data);
        navigate("/login");
      } else {
        setError(response.data.message);
        console.error("회원가입 실패:", response.data.message);
      }
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
          생년월일<a>*</a>
        </h4>
        <input
          className={s.box}
          type="date"
          name="birthdate"
          value={form.birthdate}
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
        <NotificationBox
          isNotification={form.notificationEnabled}
          handleChange={handleChange}
        />
        <button type="submit" className={s.signUpBtn} disabled={isLoading}>
          가입하기
        </button>
        <button type="button" onClick={moveToLogin} className={s.helpBtn}>
          로그인하기
        </button>
        {error && <p>오류: {error}</p>}
      </form>
    </div>
  );
}

export default RegForm;
