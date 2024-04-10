import LoginForm from "../../components/LoginForm/LoginForm";
import s from "./LoginView.module.css";

function LoginView() {
  return (
    <div className={s.wrapper}>
      <div className={s.container}>
        <div className={s.contents}>
          <img src="./image/logo.png" className={s.logo} alt="logo" />
          <h2 className={s.description}>원하는 행성으로 떠나요!</h2>
        </div>
        <div className={s.loginForm}>
          <LoginForm />
        </div>
      </div>
    </div>
  );
}

export default LoginView;
