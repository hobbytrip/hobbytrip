import MainHeader from '../../components/Main/MainHeader/MainHeader.jsx';
import MyPlanet from '../../components/Main/MyPlanet/MyPlanet.jsx';
import MyFriend from '../../components/Main/MyFriend/MyFriend.jsx';
import style from './MainView.module.css';

const MainView = () => {
  return( 
    <>
    <div className={style.wrapper}>
      <div className={style.container}>
        <MainHeader className={style.mainHeader}/>
        <MyPlanet className={style.myPlanet} />
        <MyFriend className={style.myFriend} />
      </div>
    </div>
    </>
  )
}

export default MainView;