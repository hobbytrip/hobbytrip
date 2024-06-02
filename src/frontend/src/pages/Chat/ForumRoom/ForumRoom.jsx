import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import s from "./ForumRoom.module.css";
import { axiosInstance } from "../../../utils/axiosInstance";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
import API from "../../../utils/API/TEST_API";
import useUserStore from "../../../actions/useUserStore";
import TopHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
// import ChatRoomInfo from "../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo";
import ForumModal from "../../../components/Modal/ForumModal/CreateForumModal/ForumModal";
import ForumList from "../../../components/Modal/ForumModal/ForumList/ForumList";
import axios from "axios";

const fetchForumList = async (channelId, userId) => {
  const response = await axiosInstance.get(API.READ_FORUM(channelId, userId));
  console.error("API response:", response);
  return response.data.data.content;
};

const categories = [
  { name: "🔥66챌린지", value: "CHALLENGE66" },
  { name: "🍽️식단 인증", value: "FOOD" },
  { name: "💪오운완", value: "TODAY" },
  { name: "🌞미라클모닝", value: "MIRACLE" },
  { name: "🏋️‍♀칼로리챌린지", value: "CALORIE" },
  { name: "🚶‍♀️만보챌린지", value: "MANBO" },
];

function ForumRoom() {
  const [forumList, setForumList] = useState([]);
  const [page, setPage] = useState(0);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const forumListRef = useRef(null);
  const { serverId, channelId } = useParams();
  const { userId } = useUserStore();
  const accessToken = localStorage.getItem("accessToken");

  const queryEnabled = !!userId;
  const { data, error, isLoading } = useQuery({
    queryKey: ["forum", channelId, userId],
    queryFn: () => fetchForumList(channelId, userId),
    staleTime: 1000 * 60 * 5,
    keepPreviousData: true,
    enabled: queryEnabled,
  });

  useEffect(() => {
    if (data && Array.isArray(data)) {
      setForumList((prevForumList) => [...prevForumList, ...data]);
    }
  }, [data]);

  useEffect(() => {
    if (page === 0 && forumListRef.current) {
      forumListRef.current.scrollTop = forumListRef.current.scrollHeight;
    }
  }, [forumList, page]);

  const handleNewForum = (newForum) => {
    setForumList((prevForumList) => [...prevForumList, newForum]);
    if (forumListRef.current) {
      forumListRef.current.scrollTop = forumListRef.current.scrollHeight;
    }
  };

  const updatePage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  const handleCategoryClick = (category) => {
    if (selectedCategory === category) {
      setSelectedCategory(null);
    } else {
      setSelectedCategory(category);
    }
  };

  const handleDeleteForum = async (forumId) => {
    try {
      await axios.delete(API.CUD_FORUM, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        data: {
          serverId: serverId,
          channelId: channelId,
          forumId: forumId,
          userId: userId,
        },
        withCredentials: true,
      });

      setForumList((prevForumList) =>
        prevForumList.filter((forum) => forum.forumId !== forumId)
      );
    } catch (error) {
      console.error("포럼 삭제 실패", error);
    }
  };

  const filteredForumList = selectedCategory
    ? forumList.filter((forum) => forum.forumCategory === selectedCategory)
    : forumList;

  if (isLoading) {
    return <div>Forum Loading...</div>;
  }

  if (error) {
    console.error(error.message);
  }

  return (
    <div className={s.forumWrapper}>
      <div className={s.wrapper}>
        <TopHeader />
        {/* <ChatRoomInfo /> */}
        <div className={s.forumContainer}>
          <ForumModal onNewForum={handleNewForum} />
          <div className={s.categoryButtons}>
            {categories.map((cat, index) => (
              <button
                key={cat.value}
                onClick={() => handleCategoryClick(cat.value)}
                className={`${s.categoryButton} ${
                  selectedCategory === cat.value ? s.activeCategory : ""
                }`}
              >
                {cat.name}
              </button>
            ))}
          </div>
          <div
            ref={forumListRef}
            id="forumListContainer"
            className={s.forumListContainer}
          >
            <InfiniteScrollComponent
              dataLength={filteredForumList.length}
              next={updatePage}
              hasMore={true}
              scrollableTarget="forumListContainer"
            >
              {filteredForumList.map((forum, index) => (
                <ForumList
                  key={forum.forumId}
                  forumList={[forum]}
                  handleDeleteForum={handleDeleteForum}
                />
              ))}
            </InfiniteScrollComponent>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ForumRoom;
