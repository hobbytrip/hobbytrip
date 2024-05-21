import React, { useEffect, useState } from 'react';

// dmRoomId(dm방 ID)
// userId(보낸 사람)
// Content(내용)
// Url (이동)
// alarmType(Enum : DM)

const EventComponent = () => {
  const [hidden, setHidden] = useState(document.hidden);

  useEffect(() => {
    const eventSource = new EventSource('');
    eventSource.onopen = () => {
      console.log('sse connected');
    }

    eventSource.onmessage = async (event) => {
      const res = await JSON.parse(event.data);
      let notification;

      const handleNotificationClick = () => {
        notification.current.onclick = (event) => {
          event.preventDefault();
        }
      };

      switch (res.alarmType){
        case 'DM':
          notification = new Notification(res.dmRoomID, {
            body: res.content,
            tag: res.dmRoomID,
            icon: './../../public/image/logo'
          })
          break;
        case 'Media':
          break;
        case 'GroupChat':
          break;
      }
      notification.current = new Notification(title, newOption);
      handleNotificationClick();

      if(document.hidden){
        setNotifications((prev) => [...prev, res]);
      }
      else{
        // 알림 처리되는지 확인
      }
    };

    eventSource.onerror = (error) => {
      console.log(error);
    }

    return () => eventSource.close();
  }, []);
};

export default EventComponent;