import style from './MenuView.module.css';
import ExitHeader from '../../components/MenuView/ExitHeader/ExitHeader';
import Menu from '../../components/MenuView/Menu/Menu';

// wrapper: 전체 틀
// container: 전체 크기 조정 틀
// ExitHeader: x버튼과 그 틀
// Menu: 유저, 메뉴
// logo: 아래의 로고 - 148px 75px로 했을 때 조금 눌려보여서 임의로 148px 95px로 지정
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