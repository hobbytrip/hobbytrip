import style from './MediaView.module.css';
import ChatHeader from '../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader';
import ChatHeaderModal from '../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo'
import MediaCall from '../../components/MediaView/MediaCall/MediaCall';
import MainHeader from '../../components/MainView/MainHeader/MainHeader';
import MyPlanet from '../../components/MainView/MyPlanet/MyPlanet';

const MediaView = () => {
  return(
    <>
    <div className={style.wrapper}>
    <header className={style.header}/>  
      <div className={style.container}>
        <div className={style.chatHeaders}>
        <ChatHeader />
        <ChatHeaderModal />
        </div>
        <div className={style.deskServers}>
          <MainHeader className={style.mainHeader} />
          <MyPlanet className={style.myPlanet} />
        </div>
        <MediaCall />  
      </div>
    </div>
    </>
  )
}
export default MediaView;