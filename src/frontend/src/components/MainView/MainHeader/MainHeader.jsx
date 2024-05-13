import style from './MainHeader.module.css';
import { IoMdMenu } from "react-icons/io";
import { IoSearchOutline } from "react-icons/io5";
import { useNavigate } from "react-router-dom";


const SearchForm = () => {
  // MyPlanet의 SearchForm과 동일한 클래스 이름으로 설정
  // searchContainer: 검색창의 틀
  // searchForm: 검색창 안의 input, button 범위
  // searchInput: 검색창의 input
  // searchBtn: 검색창의 돋보기 버튼
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
      <img src='./../../../../src/assets/image/logo.png'
        className={style.logo} alt='logo'/>
      <SearchForm />
      <MenuBtn />
    </div>
  );
}

export default MainHeader;