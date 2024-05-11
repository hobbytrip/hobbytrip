import style from './MediaView.module.css';
import MediaCall from '../../components/MediaView/MediaCall/MediaCall';

const MediaView = () => {
  return(
    <>
    <div className={style.wrapper}>
      <div className={style.container}>
        <MediaCall />  
      </div>
    </div>
    </>
  )
}
export default MediaView;