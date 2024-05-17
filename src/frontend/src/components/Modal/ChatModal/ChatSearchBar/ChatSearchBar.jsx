import s from "./ChatSearchBar.module.css";
import { FiSearch } from "react-icons/fi";

export default function ChatSearchBar() {
  return (
    <div className={s.wrapper}>
      <div className={s.searchContainer}>
        <input
          type="text"
          className={s.searchBar}
          placeholder="채팅 내용 검색..."
        />
        <FiSearch className={s.searchIcon} />
      </div>
    </div>
  );
}
