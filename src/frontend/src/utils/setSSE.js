const setupSSE = () => {
  let lastHeartbeat = Date.now();
  let retryCount = 0;

 
  const DMSource = new EventSource('user/api/dm/subscribe');
  const ServerSource = new EventSource('user/api/server/subscribe');

  DMSource.onopen = () => {
    console.log('DM SSE connection opened');
  };

  ServerSource.onopen =() => {
    console.log('Server SSE connection opened');
  }

  DMSource.onmessage = (event) => {
    
  };
  
  ServerSource.onmessage = (event) => {
    
  };

  DMSource.onerror = (error) => {
    console.error('SSE error:', error);
  };

  ServerSource.onerror = (error) => {
    console.error('SSE error:', error);
  };

  sse.addEventListener("heartbeat", (event) => {
    lastHeartbeat = Date.now();
  });
	
  if (retryCount >= 3) {
    return;
  }

  DMSource.onerror = (event) => {
    if (event.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeat > 1800000) {
      retryCount++;
      setTimeout(startSSE, 5000);
    }
  }
  
  ServerSource.onerror = (event) => {
    if (event.target.readyState === EventSource.CLOSED || Date.now() - lastHeartbeat > 1800000) {
      retryCount++;
      setTimeout(startSSE, 5000);
    }
  }
};

export default setupSSE;