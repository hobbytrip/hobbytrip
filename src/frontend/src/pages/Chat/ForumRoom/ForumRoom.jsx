import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import s from "./ForumRoom.module.css";
import { axiosInstance } from "../../../utils/axiosInstance";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
import API from "../../../utils/API/TEST_API";
import useUserStore from "../../../actions/useUserStore";
import TopHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ForumModal from "../../../components/Modal/ForumModal/CreateForumModal/ForumModal";
import ForumList from "../../../components/Modal/ForumModal/ForumList/ForumList";
import axios from "axios";
import emptycon from "../../../assets/image/emptyCon.jpg"; // 이미지 경로 수정

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

  const { data, error, isLoading } = useQuery({
    queryKey: ["forum", channelId, userId],
    queryFn: () => fetchForumList(channelId, userId),
    staleTime: 1000 * 60 * 5,
    keepPreviousData: true,
  });

  useEffect(() => {
    if (data && Array.isArray(data)) {
      setForumList((prevForumList) => {
        const existingIds = prevForumList.map((forum) => forum.forumId);
        const newForumData = data.filter(
          (forum) => !existingIds.includes(forum.forumId)
        );
        return [...prevForumList, ...newForumData];
      });
    }
  }, [data]);

  useEffect(() => {
    if (page === 0 && forumListRef.current) {
      forumListRef.current.scrollTop = forumListRef.current.scrollHeight;
    }
  }, [forumList, page]);

  const handleNewForum = (newForum) => {
    setForumList((prevForumList) => [newForum, ...prevForumList]);
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
            {filteredForumList.length === 0 ? (
              <div className={s.welcome}>
                <img src={emptycon} style={{ width: "160px" }} alt="empty" />
                <h3>챌린지를 시작해보세요!</h3>
              </div>
            ) : (
              <InfiniteScrollComponent
                dataLength={filteredForumList.length}
                next={updatePage}
                hasMore={true}
                scrollableTarget="forumListContainer"
              >
                <ForumList
                  forumList={filteredForumList}
                  handleDeleteForum={handleDeleteForum}
                  handleEditForum={() => {}}
                />
              </InfiniteScrollComponent>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ForumRoom;