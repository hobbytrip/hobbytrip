import { useEffect, useState } from 'react';
import { EventSourcePolyfill } from "event-source-polyfill";
import API from '../utils/API/API';
import useUserStore from '../actions/useUserStore';
import useSSEStore from '../actions/useSSEStore';

let lastHeartbeat = Date.now();
let retryCount = 0;

function useSSE() {
  const [isHidden, setIsHidden] = useState(false);
  const { userId, notificationEnabled } = useUserStore();
  const accessToken = localStorage.getItem("accessToken");

  const { setServerSource, setDmSource } = useSSEStore();

  useEffect(() => {
    if (userId && accessToken) {
      const newServerSource = new EventSourcePolyfill(API.SERVER_SSE_SUB(userId), {
        headers: {
          'Content-Type': 'text/event-stream',
          Authorization: `Bearer ${accessToken}`,
        },
        withCredentials: true,
      });
      setServerSource(newServerSource);

      const newDmSource = new EventSourcePolyfill(API.DM_SSE_SUB(userId), {
        headers: {
          'Content-Type': 'text/event-stream',
          Authorization: `Bearer ${accessToken}`,
        },
        withCredentials: true,
      });
      setDmSource(newDmSource);

      newServerSource.onopen = () => {
        console.log('Server SSE OPEN');
        console.log('server state: ', newServerSource.readyState);
      };

      newDmSource.onopen = () => {
        console.log('DM SSE OPEN');
        console.log('dm state: ', newDmSource.readyState);
      };

      newServerSource.addEventListener("heartbeat", () => {
        lastHeartbeat = Date.now();
      });

      newDmSource.addEventListener("heartbeat", () => {
        lastHeartbeat = Date.now();
      });

      newServerSource.onerror = (err) => {
        setRetry(err);
        console.error('Server SSE Error:', err);
      };

      newDmSource.onerror = (err) => {
        setRetry(err);
        console.error('DM SSE Error:', err);
      };

      newServerSource.onmessage = (event) => {
        const data = JSON.parse(event.data);
        console.log('Server SSE Message:', data);
        if (notificationEnabled && isHidden) {
          showNotification(data, 'server');
        }
      };

      newDmSource.onmessage = (event) => {
        const data = JSON.parse(event.data);
        console.log('DM SSE Message:', data);
        if (notificationEnabled && isHidden) {
          showNotification(data, 'dm');
        }
      };

      document.addEventListener("visibilitychange", () => setIsHidden(document.hidden));

      return () => {
        console.log('Closing SSE sources');
        newServerSource.close();
        newDmSource.close();
      };
    }
  }, [userId]);

  return null;
}

function showNotification(data, type) {
  const alarmData = {
    icon: '../../public/image/logo.png',
    body: data.content,
  };
  if (type === 'dm') {
    new Notification(data.userId, alarmData);
  } else if (type === 'server') {
    new Notification(data.serverId, alarmData);
  } else {
    console.log('Notification type error');
  }
}

function setRetry(err) {
  if (err.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeat > 1800000) {
    retryCount++;
    setTimeout(() => {
      if (retryCount < 3) {
        useSSE();
      }
    }, 450000);
  }
}

export default useSSE;
  