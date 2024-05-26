import MainHeader from '../../components/MainView/MainHeader/MainHeader.jsx';
import MyPlanet from '../../components/MainView/MyPlanet/MyPlanet.jsx';
import MyFriend from '../../components/MainView/MyFriend/MyFriend.jsx';
import style from './MainView.module.css';

const MainView = () => {
  return( 
    <>
    <div className={style.wrapper}>
      <div className={style.container}>
        <MainHeader />
        <MyPlanet />
        <MyFriend />
      </div>
    </div>
    </>
  )
}

export default MainView;