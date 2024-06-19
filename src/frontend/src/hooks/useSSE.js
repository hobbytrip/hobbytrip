import React, { useEffect, useState, useCallback, useRef } from 'react';
import { EventSourcePolyfill } from 'event-source-polyfill';
import API from '../utils/API/API';
import useUserStore from '../actions/useUserStore';

const useSSE = () => {
  const [isServerConnected, setIsServerConnected] = useState(false);
  const [isDmConnected, setIsDmConnected] = useState(false);
  const { userId, notificationEnabled } = useUserStore();
  const accessToken = localStorage.getItem('accessToken');
  let lastHeartbeat = Date.now();
  let retryCnt = 0;

  const serverSourceRef = useRef(null);
  const dmSourceRef = useRef(null);

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
    retryCnt = 0;
  }, []);

  const handleError = useCallback((err, type) => {
    console.error(`${type} SSE Error:`, err);
    if (type === 'server') {
      setIsServerConnected(false);
    } else if (type === 'dm') {
      setIsDmConnected(false);
    }

    if (err.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeat > 1800000) {
      retryCnt++;
      if (retryCnt < 3) {
        setTimeout(() => connectSSE(), 5000);
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

    newDmSource.onopen = () => handleOpen('dm');
    newDmSource.onerror = (err) => handleError(err, 'dm');
    
    newServerSource.onopen = () => handleOpen('server');
    newServerSource.onerror = (err) => handleError(err, 'server');

    newDmSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      console.log('DM SSE Message:', data);
      if (!notificationEnabled && document.hidden) {
        showNotification(data, 'dm');
      }
    };

    newDmSource.addEventListener('heartbeat', () => {
      lastHeartbeat = Date.now();
    });

    serverSourceRef.current = newServerSource;
    dmSourceRef.current = newDmSource;
  }, [userId]);

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
    } else {
      console.log('Notification type error');
    }
    notice.addEventListener('click', (e) => {
      // 클릭 이벤트 처리 로직 추가
    });
  };

  return { isServerConnected, isDmConnected };
};

export default useSSE;
