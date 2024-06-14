import { useEffect, useState } from "react";
import { axiosInstance } from "../../../../utils/axiosInstance";
import API from "../../../../utils/API/API";
import s from "./FriendList.module.css";
import { AiFillMessage } from "react-icons/ai";
import { VscKebabVertical } from "react-icons/vsc";
import useUserStore from "../../../../actions/useUserStore";

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
    <div>
      <FriendComponent />
    </div>
  );
}

export default FriendComponent;
