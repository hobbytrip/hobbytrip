import style from './MainHeader.module.css';

const Logo = () => {
    return (
        <img src='' className={style.logo}/>
    );
}

const SearchForm = () => {
    return (
        <form className={style.inputForm}>
            <div>
                <input type="text" placeholder="다른 행성 찾아보기" />
                <button type="submit">
                    <img src='../../../src/assets/image/main/search.png' alt="Search" />
                </button>
            </div>
        </form>
    );
}

const MenuBtn = () => {
    function onClick(){

    }
    return (
        <button className={style.menuBtn} onClick={onClick}>
            <img src='../../../src/assets/image/main/menu.png' alt="Menu" />
        </button>
    );
}

const MainHeader = () => {
    return (
        <header className={style.header}>
            <div className={style.container}>
                <Logo />
                <SearchForm />
                <MenuBtn />
            </div>
        </header>
    );
}

export default MainHeader;