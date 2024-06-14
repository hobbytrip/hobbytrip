import { useEffect, useState } from "react";
import { axiosInstance } from "../../../../utils/axiosInstance";
import API from "../../../../utils/API/API";
import useUserStore from "../../../../actions/useUserStore";
import s from "./FriendList.module.css";
import { AiFillMessage } from "react-icons/ai";
import { VscKebabVertical } from "react-icons/vsc";

function FriendComponent() {
  const [dmNotice, setDmNotice] = useState([]);
  const { userId } = useUserStore();

  const getNotice = async () => {
    try {
      const res = await axiosInstance.get(API.DM_SSE_MAIN(userId));
      if (res.data.success) {
        setDmNotice(res.data.data);
        console.log(res.data.data);
      }
    } catch (error) {
      console.error("Error fetching server notices:", error);
    }
  };

  useEffect(() => {
    getNotice();
  }, []);
  return (
    <div className={s.wrapper}>
      <li className={s.friendContainer}>
        <div className={s.friendImg}>
          <img
            src="./../../../../src/assets/image/default-logo.png"
            alt="친구 이미지"
          />
        </div>
        <div className={s.friendData}>
          <h4 className={s.friendName}>친구 </h4>
          <h5 className={s.friendIntro}>친구 소개</h5>
        </div>
        <div className={s.friendFunction}>
          <button>
            <AiFillMessage style={{ width: "15.62px", height: "15.6px" }} />
          </button>
          <button>
            <VscKebabVertical style={{ height: "15px" }} />
          </button>
        </div>
      </li>
    </div>
  );
}

export default FriendComponent;
