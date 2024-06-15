import { useEffect, useState, useCallback, useRef } from 'react';
import { EventSourcePolyfill } from 'event-source-polyfill';
import API from '../utils/API/API';
import useUserStore from '../actions/useUserStore';

const useSSE = () => {
  const [isServerConnected, setIsServerConnected] = useState(false);
  const [isDmConnected, setIsDmConnected] = useState(false);
  const { userId, notificationEnabled } = useUserStore();
  const accessToken = localStorage.getItem('accessToken');

  const serverSourceRef = useRef(null);
  const dmSourceRef = useRef(null);
  const retryCntRef = useRef(0);
  const lastHeartbeatRef = useRef(Date.now());

  const closeSSE = useCallback(() => {
    console.log('Closing SSE sources');
    if (serverSourceRef.current && serverSourceRef.current.readyState !== EventSource.CLOSED) {
      serverSourceRef.current.close();
      setIsServerConnected(false);
    }
    if (dmSourceRef.current && dmSourceRef.current.readyState !== EventSource.CLOSED) {
      dmSourceRef.current.close();
      setIsDmConnected(false);
    }
  }, []);

  const handleOpen = useCallback((type) => {
    if (type === 'server') {
      console.log('Server SSE OPEN');
      setIsServerConnected(true);
    } else if (type === 'dm') {
      console.log('DM SSE OPEN');
      setIsDmConnected(true);
    }
    retryCntRef.current = 0;
  }, []);

  const handleError = useCallback((err, type) => {
    console.error(`${type} SSE Error:`, err);
    if (type === 'server') {
      setIsServerConnected(false);
    } else if (type === 'dm') {
      setIsDmConnected(false);
    }

    if (err.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeatRef.current > 1800000) {
      retryCntRef.current++;
      if (retryCntRef.current < 3) {
        setTimeout(connectSSE, 5000);
      }
    }
  }, []);

  const connectSSE = useCallback(() => {
    closeSSE();

    const newServerSource = new EventSourcePolyfill(API.SERVER_SSE_SUB(userId), {
      headers: {
        'Content-Type': 'text/event-stream',
        Authorization: `Bearer ${accessToken}`,
      },
      withCredentials: true,
    });

    const newDmSource = new EventSourcePolyfill(API.DM_SSE_SUB(userId), {
      headers: {
        'Content-Type': 'text/event-stream',
        Authorization: `Bearer ${accessToken}`,
      },
      withCredentials: true,
    });

    newServerSource.onopen = () => handleOpen('server');
    newDmSource.onopen = () => handleOpen('dm');

    newServerSource.onerror = (err) => handleError(err, 'server');
    newDmSource.onerror = (err) => handleError(err, 'dm');

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
      lastHeartbeatRef.current = Date.now();
    });

    newDmSource.addEventListener('heartbeat', () => {
      lastHeartbeatRef.current = Date.now();
    });

    serverSourceRef.current = newServerSource;
    dmSourceRef.current = newDmSource;
  }, [closeSSE, handleOpen, handleError, notificationEnabled]);

  useEffect(() => {
    if (userId && accessToken) {
      connectSSE();
    }
    return () => {
      closeSSE();
    };
  }, [userId, accessToken, connectSSE, closeSSE]);

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

  return { isServerConnected, isDmConnected };
};

export default useSSE;
