import style from './MenuView.module.css';
import ExitHeader from '../../components/MenuView/ExitHeader/ExitHeader';
import Menu from '../../components/MenuView/Menu/Menu';

const MenuView = () => {
  return( 
    <>
    <div className={style.wrapper}>
      <div className={style.container}>
        <ExitHeader />
        <Menu />
        <div className={style.logo}>
          <img src='./../../image/logo.png'/>  
        </div>
      </div>
    </div>
    </>
  )
}

export default MenuView;