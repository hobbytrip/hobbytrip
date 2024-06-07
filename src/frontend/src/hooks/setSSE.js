import React, { useEffect, useState } from 'react';
import { EventSourcePolyfill } from "event-source-polyfill";
import API from '../utils/API/API';
import useUserStore from '../actions/useUserStore';

let lastHeartbeat = Date.now();

function setSSE() {
  const [isHidden, setIsHidden] = useState(false);
  const { userId } = useUserStore();
  const accessToken = localStorage.getItem("accessToken");


  useEffect(() => {
    const serverSource = new EventSourcePolyfill(`${API.SERVER_SSE_SUB}?userId=${userId}`, {
      headers: {
        'Content-Type': 'text/event-stream',
        Authorization: `Bearer ${accessToken}`,
      },
      withCredentials: true,
    });

    const dmSource = new EventSourcePolyfill(`${API.DM_SSE_SUB}?userId=${userId}`, {
      headers: {
        'Content-Type': 'text/event-stream',
        Authorization: `Bearer ${accessToken}`,
      },
      withCredentials: true,
    });

    console.log(serverSource);
    console.log(dmSource);

    serverSource.onopen = () => {
      console.log('server SSE OPENNNNN');
    };

    dmSource.onopen = () => {
      console.log('dm SSE OPENNNNNN');
    };

    serverSource.addEventListener("heartbeat", () => {
      lastHeartbeat = Date.now();
    });

    dmSource.addEventListener("heartbeat", () => {
      lastHeartbeat = Date.now();
    });

    let retryCount = 0;
    if (retryCount >= 3) {
      return;
    } 

    // const startSSE = () => {
    //   setSSE();
    // };

    serverSource.onerror = (err) => {
      setRetry(err);
      console.error('Server SSE Error:', err);
    };

    dmSource.onerror = (err) => {
      setRetry(err)
      console.error('DM SSE Error:', err);
    };

    serverSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      console.log('DM SSE Message:', data);
      if (isHidden || data.serverId !== currentLocation) {
        showNotification(data);
      }
    };

    dmSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      console.log('DM SSE Message:', data);
      if (isHidden || data.dmroomId !== currentLocation) {
        showNotification(data);
      }
    };

    document.addEventListener("visibilitychange", setIsHidden(document.hidden));

    return () => {
      serverSource.close();
      dmSource.close();
    };
  }, [userId, isHidden]);

  return null;
}

// 사용자에게 알림 표시
function showNotification(title, body) {
  if (Notification.permission === 'granted') {
    new Notification(title, { body });
  } else if (Notification.permission !== 'denied') {
    Notification.requestPermission().then(permission => {
      if (permission === 'granted') {
        new Notification(title, { body });
      }
    });
  }
}

function setRetry(err){
  if (err.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeat > 1800000) {
    retryCount++;
    setTimeout(startSSE, 5000);
  }
}

export default setSSE;
