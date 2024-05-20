import React, { useEffect, useState } from 'react';

const EventComponent = () => {
  const [isDocumentHidden, setIsDocumentHidden] = useState(document.hidden);

  useEffect(() => {
    const handleVisibilityChange = () => {
      setIsDocumentHidden(document.hidden);
    };

    const handleEventSourceMessage = (event) => {
      const notification = event.data;
      new Notification(notification.channelId, {
        body: notification.body,
        tag: notification.channelId,
        icon: './../../public/image/logo'
      })
      if (document.hidden) {
        
      } else {
        // 창이 활성화되어 있을 때 처리
        console.log('Document is visible, message:', event.data);
      }
    };

    // const showNotification = (title, body) => {
    //   if (Notification.permission === 'granted') {
    //     new Notification(title, { body });
    //   } else if (Notification.permission !== 'denied') {
    //     Notification.requestPermission().then(permission => {
    //       if (permission === 'granted') {
    //         new Notification(title, { body });
    //       }
    //     });
    //   }
    // };

    document.addEventListener('visibilitychange', handleVisibilityChange);

    const eventSource = new EventSource('');
    eventSource.onmessage = handleEventSourceMessage;

    eventSource.addEventListener('close', () => eventSource.close());
    return () => {
      eventSource.close();
    };
  }, []);

  return (
    <div>
      {/* 컴포넌트의 UI */}
      <p>{isDocumentHidden ? '창이 떠나있습니다.' : '창이 활성화되어 있습니다.'}</p>
    </div>
  );
};

export default EventComponent;