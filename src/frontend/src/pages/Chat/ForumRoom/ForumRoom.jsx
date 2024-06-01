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

const fetchForumList = async (channelId, userId) => {
  const response = await axiosInstance.get(API.READ_FORUM(channelId, userId));
  return response.data.data.content;
};

function ForumRoom() {
  const [forumList, setForumList] = useState([]);
  const [page, setPage] = useState(0);
  const forumListRef = useRef(null);
  const { serverId, channelId } = useParams();
  const { userId } = useUserStore();

  const { data, error, isLoading } = useQuery({
    queryKey: ["forum", channelId, userId],
    queryFn: () => fetchForumList(channelId, userId),
    enabled: !!userId,
  });

  if (isLoading) {
    return <div>Forum Loading...</div>;
  }

  if (error) {
    return <div>Fetch Forum Error: {error.message}</div>;
  }

  useEffect(() => {
    if (data && Array.isArray(data)) {
      setForumList((prevForumList) => [...prevForumList, ...data]);
    }
  }, [data]);

  useEffect(() => {
    if (page === 0 && forumListRef.current) {
      forumListRef.current.scrollTop = forumListRef.current.scrollHeight;
    }
  }, [setForumList, page]);

  const handleNewForum = (newForum) => {
    setForumList((prevForumList) => [...prevForumList, newForum]);
    if (forumListRef.current) {
      forumListRef.current.scrollTop = forumListRef.current.scrollHeight;
    }
  };

  const updatePage = () => {
    setPage((prevPage) => prevPage + 1);
  };

  return (
    <div className={s.wrapper}>
      <TopHeader />

      <div className={s.forumContainer}>
        <ForumModal />
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
            {forumList.map((forum, idx) => (
              <div key={forum.forumId} className={s.forumItem}>
                <h3>{forum.title}</h3>
                <p>Written by: {forum.writer}</p>
                <p>{forum.content}</p>
                <p>Category: {forum.forumCategory}</p>
                <p>Messages: {forum.forumMessageCount}</p>
                <div>
                  {forum.files.map((file) => (
                    <img
                      key={file.fileId}
                      src={file.fileUrl}
                      alt={`file-${file.fileId}`}
                    />
                  ))}
                </div>
                <p>Created at: {new Date(forum.createAt).toLocaleString()}</p>
                <p>Updated at: {new Date(forum.updateAt).toLocaleString()}</p>
              </div>
            ))}
          </InfiniteScrollComponent>
        </div>
      </div>
    </div>
  );
}

export default ForumRoom;
