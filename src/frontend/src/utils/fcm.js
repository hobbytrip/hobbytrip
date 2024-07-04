import { initializeApp } from "firebase/app";
import { getMessaging, onMessage } from "firebase/messaging";

const firebaseConfig = {
  apiKey: "AIzaSyA4PFU_lXDtho7MT03-8T-VErEEva8Z6II",
  authDomain: "fittrip-c05e2.firebaseapp.com",
  projectId: "fittrip-c05e2",
  storageBucket: "fittrip-c05e2.appspot.com",
  messagingSenderId: "214859641908",
  appId: "1:214859641908:web:861c865fab5247fb360661",
  measurementId: "G-PJ89CGGRX1"
};

const app = initializeApp(firebaseConfig);
export const messaging = getMessaging(app);

onMessage(messaging, (payload) => {
  console.log("Message received. ", payload);
  
  const titleData = payload.notification.title;
  const bodyData = payload.notification.body;
  let title;
  switch(titleData){
    case 'SERVER':
      title = '서버 이름'
      break;
    case 'FRIEND':
      title = '친구 요청'
      break;
    case 'DM':
      title = 'DM'
      break;
    default:
      title = "FitTrip"
      break;
  }
  const body = {
    body: payload.notification.body,
    icon: 'http://localhost:3000/image/logo.png'
  };

 const notification = new Notification(title, body);

 notification.onclick = (event) => {
  event.preventDefault();
  console.error('clickeeeeed');
  notification.close();
 }
 setTimeout(() => {
  notification.close();
}, 3000);
});
