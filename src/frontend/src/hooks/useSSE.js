import { useEffect, useState } from 'react';
import { EventSourcePolyfill } from 'event-source-polyfill';
import API from '../utils/API/API';
import useUserStore from '../actions/useUserStore';

let lastHeartbeat = Date.now();
let retryCnt = 0;

const useSSE = () => {
  const [serverSource, setServerSource] = useState(null);
  const [dmSource, setDmSource] = useState(null);
  const [isServerConnected, setIsServerConnected] = useState(false);
  const [isDmConnected, setIsDmConnected] = useState(false);
  const { userId, notificationEnabled } = useUserStore();
  const accessToken = localStorage.getItem('accessToken');

  useEffect(() => {
    if (userId && accessToken) {
      connectSSE();
    }
    return () => {
      closeSSE();
    };
  }, [userId, accessToken]);

  const connectSSE = () => {
    if (serverSource && serverSource.readyState !== EventSource.CLOSED) {
      serverSource.close();
    }
    if (dmSource && dmSource.readyState !== EventSource.CLOSED) {
      dmSource.close();
    }
  
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
      setIsServerConnected(true);
      console.log(`Server SSE ReadyState: ${newServerSource.readyState}`);
    };
  
    newDmSource.onopen = () => {
      console.log('DM SSE OPEN');
      setIsDmConnected(true);
      console.log(`DM SSE ReadyState: ${newDmSource.readyState}`);
    };
  
    newServerSource.onerror = (err) => {
      console.error('Server SSE Error:', err);
      setIsServerConnected(false);
      console.log(`Server SSE ReadyState: ${newServerSource.readyState}`);
  
      if (err.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeat > 1800000) {
        retryCnt++;
        if (retryCnt < 3) {
          setTimeout(connectSSE, 5000);
        }
      }
    };
  
    newDmSource.onerror = (err) => {
      console.error('DM SSE Error:', err);
      setIsDmConnected(false);
      console.log(`DM SSE ReadyState: ${newDmSource.readyState}`);
  
      if (err.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeat > 1800000) {
        retryCnt++;
        if (retryCnt < 3) {
          setTimeout(connectSSE, 5000);
        }
      }
    };
  
    newServerSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      console.log('Server SSE Message:', data);
      if (!notificationEnabled && document.hidden) {
        showNotification(data, 'server');
      }
    };
  
    newDmSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      console.log('DM SSE Message:', data);
      if (!notificationEnabled && document.hidden) {
        showNotification(data, 'dm');
      }
    };
  
    newServerSource.addEventListener('heartbeat', () => {
      lastHeartbeat = Date.now();
    });
  
    newDmSource.addEventListener('heartbeat', () => {
      lastHeartbeat = Date.now();
    });
  
    setServerSource(newServerSource);
    setDmSource(newDmSource);
  };

  const closeSSE = () => {
    console.log('Closing SSE sources');
    if (serverSource && serverSource.readyState !== EventSource.CLOSED) {
      serverSource.close();
      setIsServerConnected(false);
      console.log(`Server SSE ReadyState: ${serverSource.readyState}`);
    }
    if (dmSource && dmSource.readyState !== EventSource.CLOSED) {
      dmSource.close();
      setIsDmConnected(false);
      console.log(`DM SSE ReadyState: ${dmSource.readyState}`);
    }
  };

  const showNotification = (data, type) => {
    const alarmData = {
      icon: '../../public/image/logo.png',
      body: data.content,
    };
    let notice;
    if (type === 'dm') {
      notice = new Notification(data.userId, alarmData);
    } else if (type === 'server') {
      notice = new Notification(data.serverId, alarmData);
    } else {
      console.log('Notification type error');
    }
    notice.addEventListener('click', (e) => {
      // 필요한 경우 클릭 이벤트 로직 추가
    });
  };

  return { isServerConnected, isDmConnected, serverSource, dmSource };
};

export default useSSE;
