import style from './MyFriend.module.css';
import { IoSearchOutline } from "react-icons/io5";
import { AiFillMessage } from "react-icons/ai";
import { VscKebabVertical } from "react-icons/vsc";


const FriendMenu = () => {
  // friendMenuContainer: 내 친구, 모두, 온라인, 대기 중, 친구 추가하기 를 포함한 컨테이너
  // friendMenu: 모두, 온라인, 대기 중, 친구 추가하기 ul 
  // addFriend: 친구 추가하기만 적용되는 class
  return (
    <div className={style.friendMenuContainer}>    
      <h3> 내 친구</h3>
      <ul className={style.friendMenuList}>
        <li><button><h4>모두</h4></button></li>
        <li><button><h4>온라인</h4></button></li>
        <li><button><h4>대기 중</h4></button></li>
        <li>
          <button className={style.addFriend}><h4>친구 추가하기</h4></button>
        </li>
      </ul>
    </div>
  );
}

const FriendSearch = () => {
  // MainHeader의 SearchForm과 동일한 클래스 이름으로 설정
  // searchContainer: 검색창의 틀
  // searchForm: 검색창 안의 input, button 범위
  // searchInput: 검색창의 input
  // searchBtn: 검색창의 돋보기 버튼
  return (
    <div className={style.searchContainer}>
      <form className={style.searchForm}>
        <input type="text" placeholder="친구 검색하기" className={style.searchInput} />
        <button type="submit" className={style.searchBtn}>
          <IoSearchOutline style={{ width: '12.3px', height: '12px', color: '#434343' }} />
        </button>
      </form>
    </div>
  );
}

const FriendList = () => {
  // friendListContainer: 친구 목록 전체 틀
  // friendContainer: 친구 하나의 틀
  // friendImg: 친구의 사진
  // friendData: 친구 이름, 한소가 들어간 틀
  // friendName: 친구 이름
  // friendIntro: 친구 한소
  // friendFunction: 채팅, 더보기 틀
  return(
    <>
      <div className={style.friendListContainer}>
        <li className={style.friendContainer}>
          <div className={style.friendImg}>
            <img  
              src='./../../../../src/assets/image/main/logo.png'
              alt="친구 이미지"/>  
          </div>
          <div className={style.friendData}>
            <h4 className={style.friendName}>친구 </h4>  
            <h5 className={style.friendIntro}>친구 소개</h5>
          </div>
          <div className={style.friendFunction}>
            <button>
              <AiFillMessage style={{ width: '15.62px', height: '15.6px' }} />
            </button>
            <button>
              <VscKebabVertical style={{ height: '15px' }} />
            </button>
          </div>
        </li>
      </div>
    </>
  )
}

const MyFriend = () =>{
  return(
    <div className={style.wrapper}>
      <FriendMenu />
      <FriendSearch />
      <FriendList />
    </div>
  )
}

export default MyFriend;