import { useNavigate } from 'react-router-dom';
import style from './ExitHeader.module.css';
import { IoClose } from "react-icons/io5";

// wrapper: x 버튼이 있는 곳의 틀
// exiBtn: x 버튼
const ExitHeader = () =>{
  const nav = useNavigate();
  return(
    <div className={style.wrapper}>
      <button className={style.exitBtn} onClick={() => nav(-1)}>
        <IoClose style={{ width: '18px', height: '18px' }}/>
      </button>
    </div>
  )
}

export default ExitHeader;