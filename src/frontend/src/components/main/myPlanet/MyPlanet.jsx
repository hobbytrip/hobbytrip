import style from './MyPlanet.module.css';

const Leftbtn = () => {
    return (
        <button className={style.leftBtn}>
            <h3> ‹ </h3>
        </button>
    );
}
const Rightbtn = () => {
    return (
        <button className={style.rightBtn}>
            <h3> › </h3>
        </button>
    );
}
const CreatePlanetbtn = () => {
    return (
        <button className={style.createPlanet}>
            <h1> + </h1>
        </button>
    );
}

const MyPlanet = () => {
    return (
        <div className={style.container}>
            <h1> 내 행성 </h1>
            <div className={style.myPlanetList}>  
                <Leftbtn />
                <div className={style.planetList}>
                    <div>  
                        <CreatePlanetbtn />
                    </div>
                </div>
                <Rightbtn />  
            </div>
        </div>
    );
}

export default MyPlanet;