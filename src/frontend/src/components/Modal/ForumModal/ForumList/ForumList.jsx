import React from "react";
import { IoPlanetSharp } from "react-icons/io5";
import s from "./ForumList.module.css";
import useFormatDate from "../../../../hooks/useFormatDate";
import emptycon from "../../../../assets/image/emptyCon.jpg";
import { FaTrashAlt } from "react-icons/fa";
import { MdEdit } from "react-icons/md";
import { useNavigate, useParams } from "react-router-dom";
import useCategoryName from "../../../../hooks/useCategoryName";

const ForumList = ({ forumList, handleDeleteForum, handleEditForum }) => {
  const navigate = useNavigate();
  const { serverId, channelId } = useParams();

  const onDeleteForum = (forumId) => {
    handleDeleteForum(forumId);
  };

  const onEditForum = (forumId) => {
    handleEditForum(forumId);
  };

  //포럼 채팅으로 넘어가기
  const moveToForumChat = (forum) => {
    navigate(`/${serverId}/${channelId}/forum/${forum.forumId}/chat`, {
      state: { forum },
    });
    console.log("forum:", forum);
  };

  const renderList = () => {
    return (
      <>
        {forumList.length === 0 ? (
          <div className={s.welcome}>
            <img src={emptycon} style={{ width: "160px" }} alt="empty" />
            <h2>챌린지를 시작해보세요</h2>
          </div>
        ) : (
          forumList.map((forum, index) => (
            <div
              key={forum.forumId}
              className={s.forumBox}
              onClick={() => moveToForumChat(forum)}
            >
              <div className={s.container}>
                <h4 className={s.category} style={{ marginBottom: "5px" }}>
                  {useCategoryName(forum.forumCategory)}
                </h4>

                <h2 className={s.title} style={{ marginBottom: "5px" }}>
                  {forum.title}
                </h2>
                <div className={s.detail}>
                  <h3 className={s.writer} style={{ fontWeight: "500" }}>
                    {forum.writer}
                  </h3>
                  <h3
                    style={{
                      color: "#434343",
                      fontWeight: "normal",
                    }}
                  >
                    {forum.content}
                  </h3>
                </div>
                <div className={s.comments} style={{ marginTop: "5px" }}>
                  <IoPlanetSharp
                    style={{ float: "left", color: "#0000008e" }}
                  />
                  <h3
                    style={{
                      color: "#434343",
                      fontWeight: "300",
                      fontSize: "14px",
                      float: "left",
                      marginRight: "10px",
                    }}
                  >
                    {forum.forumMessageCount}
                  </h3>
                  <h3
                    className={s.date}
                    style={{
                      color: "#434343",
                      fontWeight: "400",
                      fontSize: "14px",
                    }}
                  >
                    : {useFormatDate(forum.createAt)}
                  </h3>
                </div>
              </div>
              <div className={s.imagePreview}>
                {forum.files.map((file) => (
                  <img
                    key={file.fileId}
                    src={file.fileUrl}
                    alt={`file-${file.fileId}`}
                    style={{
                      width: "90px",
                      position: "absolute",
                      right: "10px",
                    }}
                  />
                ))}
              </div>
              <div className={s.modals}>
                <MdEdit onClick={() => onEditForum(forum.forumId)} />
                <FaTrashAlt
                  onClick={(e) => {
                    e.stopPropagation();
                    onDeleteForum(forum.forumId);
                  }}
                  style={{
                    cursor: "pointer",
                    marginLeft: "5px",
                    // color: "#dfdfdf",
                  }}
                />
              </div>
            </div>
          ))
        )}
      </>
    );
  };

  return <div>{renderList()}</div>;
};

export default ForumList;
