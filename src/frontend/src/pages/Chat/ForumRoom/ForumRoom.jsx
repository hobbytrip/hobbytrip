import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import s from "./ForumRoom.module.css";
import { axiosInstance } from "../../../utils/axiosInstance";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";
import API from "../../../utils/API/API";
import useUserStore from "../../../actions/useUserStore";
import useForumListStore from "../../../actions/useForumListStore";
import TopHeader from "../../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader";
import ChatRoomInfo from "../../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo";
import ForumModal from "../../../components/Modal/ForumModal/CreateForumModal/ForumModal";
import ForumList from "../../../components/Modal/ForumModal/ForumList/ForumList";
import axios from "axios";
import emptycon from "../../../assets/image/emptyCon.jpg"; // 이미지 경로 수정

const fetchForumList = async (channelId, userId) => {
  const response = await axiosInstance.get(API.READ_FORUM(channelId, userId));
  console.log(API.READ_FORUM(channelId, userId));
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
  const { serverId, channelId } = useParams();
  const { userId } = useUserStore();
  const {
    forumLists,
    setForumList,
    addForum,
    removeForum,
    // resetForumList,
  } = useForumListStore();
  const forumList = forumLists[channelId] || [];
  const [page, setPage] = useState(0);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const forumListRef = useRef(null);
  const accessToken = localStorage.getItem("accessToken");

  const { data, error, isLoading } = useQuery({
    queryKey: ["forum", channelId, userId],
    queryFn: () => fetchForumList(channelId, userId),
    staleTime: 1000 * 60 * 5,
    keepPreviousData: true,
  });

  // useEffect(() => {
  //   resetForumList(channelId);
  // }, [channelId]);

  useEffect(() => {
    console.log("channelId", channelId);

    if (data && Array.isArray(data)) {
      console.log("data", data);
      setForumList(channelId, data);
    }
  }, [data, channelId, setForumList]);

  useEffect(() => {
    if (page === 0 && forumListRef.current) {
      forumListRef.current.scrollTop = forumListRef.current.scrollHeight;
    }
  }, [forumList, page]);

  const handleNewForum = (newForum) => {
    addForum(channelId, newForum);
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

  const handleEditForum = async (forumId, newTitle, newContent) => {
    try {
      const formdata = new FormData();
      const messageBody = {
        userId,
        serverId,
        channelId,
        forumId: forumId,
        title: newTitle,
        content: newContent,
      };
      const jsonMsg = JSON.stringify(messageBody);

      const requestDto = new Blob([jsonMsg], { type: "application/json" });
      formdata.append("requestDto", requestDto);
      await axios.patch(API.CUD_FORUM, formdata, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${accessToken}`,
        },
        withCredentials: true,
      });
    } catch (error) {
      console.error("포럼 수정 실패", error);
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

      removeForum(channelId, forumId);
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
        <ChatRoomInfo />
        <div className={s.forumContainer}>
          <ForumModal onNewForum={() => handleNewForum} />
          <div className={s.categoryButtons}>
            {categories.map((cat) => (
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
                  handleEditForum={handleEditForum}
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
