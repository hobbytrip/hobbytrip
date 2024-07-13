import style from './MediaView.module.css';
import ChatHeader from '../../components/Common/ChatRoom/CommunityChatHeader/ChatHeader';
import ChatHeaderModal from '../../components/Modal/ChatModal/ChatRoomInfo/ChatRoomInfo'
import MediaCall from '../../components/MediaView/MediaCall/MediaCall';
import MainHeader from '../../components/MainView/MainHeader/MainHeader';
import MyPlanet from '../../components/MainView/MyPlanet/MyPlanet';
import CategoryList from '../../components/TYPE/Community/CategoryList/CategoryList';
import FriendsList from '../../components/TYPE/Community/FriendsList/FriendsList';

const MediaView = () => {
  return(
    <>
    <div className={style.wrapper}>
    <header className={style.header}/>  
      <div className={style.container}>
        <div className={style.mediaType}>
          <div className={style.chatHeaders}>
            <ChatHeader />
            <ChatHeaderModal />
          </div>
          <MediaCall />
        </div>
        <div className={style.deskType}>
          <div className={style.deskServers}>
            <MainHeader className={style.mainHeader} />
            <MyPlanet className={style.myPlanet} />
          </div>
          <div className={style.deskElements}> 
            <CategoryList className={style.categoryList}/>
            <MediaCall className={style.mediaCall}/>   
          </div>
        </div>
      </div>
    </div>
    </>
  )
}
export default MediaView;