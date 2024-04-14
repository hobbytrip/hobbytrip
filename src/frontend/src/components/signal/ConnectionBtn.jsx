import { joinSession, leaveSession } from './session';
import useSessionConnectedStore from "../../../actions/sessionState";

const { sessionConnected, setSessionConnected } = useSessionConnectedStore(store => store);

const StartBtn = () => {
    function onClick(){
        joinSession();
        setSessionConnected(true);
    }
    return(
        <button onClick={onClick}> Start </button>
    )
}

const EndBtn = () => {
    function onClick(){
        leaveSession();
        setSessionConnected(false);
    }
    return(
        <button onClick={onClick}> End </button>
    )
}

const ConnectionBtn = () => {
    return(
        <>
        { sessionConnected ? (
            <EndBtn />
        ) : (
            <StartBtn />
        )}
        </>
    )
}

export default ConnectionBtn;