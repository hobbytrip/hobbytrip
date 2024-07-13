import React, { useState } from "react";
import style from './AddServer.module.css'
import CreateServer from "../CreateServer/CreateServer";
import JoinServer from "../AddServer/JoinServer/JoinServer";

function CreateServer() {
  const [isCreatingServer, setIsCreatingServer] = useState(true);

  return (
    <>
    <div className={style.formWrapper}>
      {isCreatingServer ? (
        <>
          <CreateServer />
        
        <h5 onClick={() => setIsCreatingServer(!isCreatingServer)}>
          {isCreatingServer ? "초대 링크가 있으신가요?" : "채널 생성하러 가기"}
        </h5>
        
        </>
      ) : (
        <JoinServer />
      )}
      <div>
        <h5 onClick={() => setIsCreatingServer(!isCreatingServer)}>
          {isCreatingServer ? "초대 링크가 있으신가요?" : "채널 생성하러 가기"}
        </h5>
      </div>
    </div>
    </>
  );
}

export default CreateServer;