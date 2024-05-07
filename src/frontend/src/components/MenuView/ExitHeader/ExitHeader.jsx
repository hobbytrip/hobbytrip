import style from './ExitHeader.module.css';
import { IoClose } from "react-icons/io5";

const ExitHeader = () =>{
  return(
    <div className={style.wrapper}>
      <button className={style.exitBtn}>
        <IoClose style={{ width: '18px', height: '18px' }}/>
      </button>
    </div>
  )
}

export default ExitHeader;