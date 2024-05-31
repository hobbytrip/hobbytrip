import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import s from "./ForumRoom.module.css";
import axios from "axios";
import ForumType from "../../../components/Modal/ForumModal/ForumType/ForumType";
import InfiniteScrollComponent from "../../../components/Common/ChatRoom/InfiniteScrollComponent";

const fetchForum = async (forumId) => {
  const response = await axios.get(
    `https://fittrip.site/api/community/forum/${forumId}`
  );
  return response.data;
};

function ForumRoom({ userId }) {
  const [forumList, setForumList] = useState([]);
  const [page, setPage] = useState(0);
  const forumListRef = useRef(null);
  const forumId = 1;

  const { data, error, isLoading } = useQuery({
    queryKey: ["forum", forumId],
    queryFn: () => fetchForum(forumId),
  });
  if (isLoading) {
    return <div>Forum Loading...</div>;
  }
  if (error) {
    return <div>Fetch Forum Error: {error.message}</div>;
  }

  useEffect(() => {
    if (data && Array.isArray(data)) {
      setForumList((prevForumList) => [...data, ...prevForumList]);
    }
  }, [data]);

  useEffect(() => {
    if (page === 0 && forumListRef.current) {
      forumListRef.current.scrollTop = forumListRef.current.scrollHeight;
    }
  }, [setForumList, page]);

  const handleNewForum = (newForum) => {
    setChatList((prevForumList) => [...prevForumList, newForum]);
    if (forumListRef.current) {
      forumListRef.current.scrollTop = forumListRef.current.scrollHeight;
    }
  };

  const updatePage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  return (
    <div className={s.wrapper}>
      <div className={s.chatContainer}>
        <ForumType />
        <div
          ref={forumListRef}
          id="forumListContainer"
          className={s.forumListContainer}
          style={{ overflowY: "auto", height: "530px" }}
        >
          <InfiniteScrollComponent
            dataLength={forumList.length}
            next={updatePage}
            hasMore={true}
            scrollableTarget="forumListContainer"
          >
            {forumList.map((forum, idx) => {
              {
                forum.forumId;
              }
            })}
          </InfiniteScrollComponent>
        </div>
      </div>
    </div>
  );
}

export default ForumRoom;
