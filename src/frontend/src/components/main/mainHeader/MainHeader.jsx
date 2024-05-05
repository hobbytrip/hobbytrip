import style from './MainHeader.module.css';
import { IoMdMenu } from "react-icons/io";
import { IoSearchOutline } from "react-icons/io5";
import { useNavigate } from "react-router-dom";


const SearchForm = () => {
  return (
    <form>
      <div className={style.searchContainer}>
        <div className={style.searchForm}>
          <input type="text" placeholder="다른 행성 찾아보기" className={style.searchInput}/>
          <button type="submit" className={style.searchBtn}>
            <IoSearchOutline style={{ width: '12.3px', height: '12px', color: '#E6E6E6' }}  />
          </button>
        </div>
      </div>
    </form>
  );
}

const MenuBtn = () => {
  const navigator = useNavigate();
  function onClick(){
    navigator('/menu');
  }
  return (
    <button className={style.menuBtn} onClick={onClick}>
      <IoMdMenu style={{ width: '15px', height: '12.63px' }} color='white'/>
    </button>
  );
}

const MainHeader = () => {
  return (
    <div className={style.wrapper}>
      <img src='./../../../../src/assets/image/main/logo.png'
        className={style.logo} alt='logo'/>
      <SearchForm />
      <MenuBtn />
    </div>
  );
}

export default MainHeader;