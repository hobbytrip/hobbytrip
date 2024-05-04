import MainHeader from '../../components/main/MainHeader/MainHeader.jsx';
import MyPlanet from '../../components/main/MyPlanet/MyPlanet.jsx';
import MyFriend from '../../components/main/MyFriend/MyFriend.jsx';
import style from './MainView.module.css';

const MainView = () => {
    return( 
        <>
        <div className={style.container}>
            <MainHeader className={style.mainHeader}/>
            <MyPlanet className={style.myPlanet} />
            <MyFriend className={style.myFriend} />
        </div>
        </>
    )
}

export default MainView;