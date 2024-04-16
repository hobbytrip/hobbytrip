const CameraBtn = () => {
    const onClick = () => {
    }
    return(
        <>
        <button >
            <img src='../../../src/assets/media/startCamera.png' />
        </button>
        </>
    )
}

const MiceBtn = () => {
    const onClick = () => {
    }
    return(
        <>
        {/* { mice ? (
            <button onClick={onClick}> Mice Off </button>
        ) : (
            <button onClick={onClick}> Mice On </button>
        )} */}
        <button>
            <img src='../../../src/assets/media/startMice.png' />
        </button>
        </>
    )
}
    
// const VideoComponent = () =>{
//     return(
//         <>
//         <openvidu-webcomponent style="display: none"/> 
//         <video autoPlay />
//         <div>
//             <CameraBtn />
//             <MiceBtn />
//         </div>
//         </>
//     )
// }

export default { MiceBtn, CameraBtn };