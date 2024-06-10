import s from "./ForumThumb.module.css";
import icon from "../../../../assets/image/forumLion.jpg";
import useCategoryName from "../../../../hooks/useCategoryName";
import { AiOutlineClose } from "react-icons/ai";
import { useNavigate } from "react-router-dom";

function ForumDetail({ forum }) {
  const navigate = useNavigate();
  const categoryName = useCategoryName(forum.forumCategory);
  const handleMoveToBack = () => {
    navigate(-1);
  };
  return (
    <div className={s.wrapper}>
      <div className={s.header}>
        <h3>{forum.title}</h3>
        <AiOutlineClose className={s.closebtn} onClick={handleMoveToBack} />
      </div>
      <img src={icon} className={s.icon} alt="forumIcon" />
      <h1>{forum.title}</h1>
      <h4 className={s.category} style={{ marginBottom: "5px" }}>
        {categoryName}
      </h4>
    </div>
  );
}

export default ForumDetail;
