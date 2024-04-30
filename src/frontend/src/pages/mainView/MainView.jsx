import MainHeader from '../../components/main/mainHeader/MainHeader';
import MyPlanet from '../../components/main/myPlanet/MyPlanet.jsx';
import FriendList from '../../components/main/friendList/FriendList.jsx';
import style from './MainView.module.css';

const MainView = () => {
    return( 
        <>
        <div className={style.container}>
            <MainHeader className={style.header}/>
            <MyPlanet />
            <FriendList />
        </div>
        </>
    )
}

export default MainView;