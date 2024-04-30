import style from './FriendList.module.css';

const FriendMenu = () => {
    return (
        <div className={style.friendMenu}>    
            <h1> 내 친구</h1>
            <ul>
                <li><button> <h3> 모두 </h3> </button></li>
                <li><button> <h3> 온라인 </h3> </button></li>
                <li><button> <h3> 대기 중 </h3> </button></li>
                <li><button className={style.addFriend}> <h3> 친구 추가하기 </h3> </button></li>
            </ul>
        </div>
    );
}

const FriendSearch = () => {
    return (
        <div className={style.friendSearch}>
            <form>
                <input type="text" placeholder="친구 검색하기" />
                <button type="submit">
                    <img src='../../../src/assets/image/main/search.png' alt="Search" />
                </button>
            </form>
        </div>
    );
}

const FriendList = () =>{
    return(
        <div className={style.container}>
            <FriendMenu className={style.FriendMenu}/>
            <FriendSearch />
            <div className={style.friendList}>
                <ul>
                </ul>
            </div>
        </div>
    )
}

export default FriendList;