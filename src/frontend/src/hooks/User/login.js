import useAuthStore from "../../actions/useAuthStore";
const Login = async (email, password) => {
  const setTokens = useAuthStore((state) => state.setTokens);
  try {
    const response = await axios.post("/user/login", {
      email,
      password,
    });
    if (response.status == 200) {
      const accesstoken = localStorage.setItem("accesstoken", accesstoken);
      const refreshtoken = localStorage.setItem("refreshtoken", refreshtoken);
      setTokens(accesstoken, refreshtoken);
    }
  } catch (error) {
    console.error("로그인 실패", error);
    throw error;
  }
};
export default Login;
