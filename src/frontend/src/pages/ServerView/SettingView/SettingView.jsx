import style from './SettingView.module.css';
import ServerSetting from '../../../components/ServerView/SeverSetting/ServerSetting';

const SettingView = () => {
  return(
    <>
    <div className={style.wrapper}>
      <div className={style.container}>
        <div className={style.manage}>
          <h2> 내 행성 관리 </h2>
        </div>
        <div className={style.setting}>
          <ServerSetting />
        </div>
      </div>
    </div>
    </>
  )
}

export default SettingView;