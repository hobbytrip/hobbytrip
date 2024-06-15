import React, { useState } from "react";
import { IoPlanetSharp } from "react-icons/io5";
import s from "./ForumList.module.css";
import useFormatDate from "../../../../hooks/useFormatDate";
import emptycon from "../../../../assets/image/emptyCon.jpg";
import { FaTrashAlt } from "react-icons/fa";
import { MdEdit } from "react-icons/md";
import { useNavigate, useParams } from "react-router-dom";
import useCategoryName from "../../../../hooks/useCategoryName";

function ForumList({ forumList, handleDeleteForum, handleEditForum }) {
  const navigate = useNavigate();
  const { serverId, channelId } = useParams();
  const [editingForumId, setEditingForumId] = useState(null);
  const [editTitle, setEditTitle] = useState("");
  const [editContent, setEditContent] = useState("");

  const onDeleteForum = (forumId, event) => {
    event.stopPropagation();
    handleDeleteForum(forumId);
  };

  const onEditForum = (forumId, event) => {
    event.stopPropagation();
    const forum = forumList.find((f) => f.forumId === forumId);
    setEditingForumId(forumId);
    setEditTitle(forum.title);
    setEditContent(forum.content);
  };

  const handleSaveEdit = (event) => {
    event.stopPropagation();
    handleEditForum(editingForumId, editTitle, editContent);
    setEditingForumId(null);
    setEditTitle("");
    setEditContent("");
  };

  const handleCancelEdit = (event) => {
    event.stopPropagation();
    setEditingForumId(null);
    setEditTitle("");
    setEditContent("");
  };

  //포럼 채팅으로 넘어가기
  const moveToForumChat = (forum) => {
    if (editingForumId !== forum.forumId) {
      navigate(`/${serverId}/${channelId}/forum/${forum.forumId}/chat`, {
        state: { forum },
      });
    }
  };

  return (
    <div>
      {forumList.length === 0 ? (
        <div className={s.welcome}>
          <img src={emptycon} style={{ width: "160px" }} alt="empty" />
          <h2>챌린지를 시작해보세요</h2>
        </div>
      ) : (
        forumList.map((forum) => (
          <div
            key={forum.forumId}
            className={s.forumBox}
            onClick={() => moveToForumChat(forum)}
          >
            <div className={s.container}>
              {editingForumId === forum.forumId ? (
                <div>
                  <input
                    type="text"
                    value={editTitle}
                    onChange={(e) => setEditTitle(e.target.value)}
                    className={s.editInput}
                    onClick={(e) => e.stopPropagation()}
                  />
                  <textarea
                    value={editContent}
                    onChange={(e) => setEditContent(e.target.value)}
                    className={s.editTextarea}
                    onClick={(e) => e.stopPropagation()}
                    style={{ marginTop: "10px" }}
                  />
                  <button onClick={handleSaveEdit}>Save</button>
                  <button onClick={handleCancelEdit}>Cancel</button>
                </div>
              ) : (
                <div>
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
              )}
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
              <MdEdit
                onClick={(e) => onEditForum(forum.forumId, e)}
                style={{
                  cursor: "pointer",
                }}
              />
              <FaTrashAlt
                onClick={(e) => onDeleteForum(forum.forumId, e)}
                style={{
                  cursor: "pointer",
                  marginLeft: "5px",
                }}
              />
            </div>
          </div>
        ))
      )}
    </div>
  );
}

export default ForumList;
