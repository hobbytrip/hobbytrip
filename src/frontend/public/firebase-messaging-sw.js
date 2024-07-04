importScripts('https://www.gstatic.com/firebasejs/10.1.0/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/10.1.0/firebase-messaging-compat.js');

const firebaseConfig = {
  apiKey: "AIzaSyA4PFU_lXDtho7MT03-8T-VErEEva8Z6II",
  projectId: "fittrip-c05e2",
  messagingSenderId: "214859641908",
  appId: "1:214859641908:web:861c865fab5247fb360661",
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

// 외부 화면 메시지 처리
messaging.onBackgroundMessage((payload) => {
  console.log('Background message ', payload);
  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
    icon: 'http://localhost:3000/image/logo.png',
    tag: notificationTitle
  };

  self.registration.showNotification(notificationTitle, notificationOptions);

  setTimeout(() => {
    self.registration.getNotifications({ tag: notificationTitle }).then(notifications => {
      notifications.forEach(notification => {
        notification.close();
      });
    });
  }, 3000);
});

self.addEventListener("notificationclick", function(event) {
  console.log('Notification clicked');
  event.notification.close();

  event.waitUntil(
    clients.matchAll({ type: 'window', includeUncontrolled: true }).then(windowClients => {
      for (const client of windowClients) {
        if (client.url.startsWith('http://localhost:3000') && 'focus' in client) {
          return client.focus();
        }
      }
      if (clients.openWindow) {
        return clients.openWindow('http://localhost:3000');
      }
    })
  );
});
