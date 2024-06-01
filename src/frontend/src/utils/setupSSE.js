import useNotificationStore from '../actions/useNotificationStore';

export const setupSSE = () => {
  const addNotification = useNotificationStore.getState().addNotification;
  const updateConnectionStatus = useNotificationStore.getState().updateConnectionStatus;

  const eventSource = new EventSource('https://your-server-url.com/sse-endpoint');

  eventSource.onopen = () => {
    console.log('SSE connection opened');
    updateConnectionStatus(true);
  };

  eventSource.onmessage = (event) => {
    const data = JSON.parse(event.data);
    if (data.roomId) {
      addNotification(data.roomId);
    }
  };

  eventSource.onerror = (error) => {
    console.error('SSE error:', error);
    updateConnectionStatus(false);
  };

  return () => {
    eventSource.close();
  };
};
