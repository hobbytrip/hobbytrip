
### DM 메시지 collection 
```json
{
  "_id": 55,
  "dmRoomId": 1,
  "parentId": 0,
  "userId": 1,
  "profileImage": "profile.jpg",
  "writer": "JohnDoe",
  "content": "Hello!",
  "chatType": "DM",
  "actionType": "SEND",
  "isDeleted": false,
  "files": [
    {
      "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/chat/dacea1c5-e176-4d0c-8b20-a4c14be3a39c.png",
      "storeFileName": "chat/dacea1c5-e176-4d0c-8b20-a4c14be3a39c.png",
      "originalFilename": "테스트이미지.png"
    },
    {
      "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/chat/7a13cce5-cf6b-4ce5-a768-c0527154a109.png",
      "storeFileName": "chat/7a13cce5-cf6b-4ce5-a768-c0527154a109.png",
      "originalFilename": "bd476f6e-b893-4402-8999-749031421f13.png"
    }
  ],
  "createdAt": {
    "$date": "2024-05-26T08:45:17.476Z"
  }
}
```

### 포럼 메시지 collection 
```json
{
  "_id": 76,
  "serverId": 1,
  "channelId": 9,
  "forumId": 2,
  "userId": 25,
  "parentId": 0,
  "profileImage": "ho",
  "writer": "바밤바",
  "content": "댓글",
  "chatType": "FORUM",
  "actionType": "SEND",
  "isDeleted": false,
  "files": [
    {
      "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/chat/998eabcd-f1a5-4ced-a4f5-32912a3a9142.png",
      "storeFileName": "chat/998eabcd-f1a5-4ced-a4f5-32912a3a9142.png",
      "originalFilename": "테스트이미지.png"
    }
  ],
  "createdAt": {
    "$date": "2024-05-26T08:46:04.130Z"
  }
}
```

### 서버 메시지 collection 
```json
{
  "_id": 130,
  "serverId": 1,
  "channelId": 4,
  "userId": 1,
  "parentId": 0,
  "profileImage": "ho",
  "writer": "메로나",
  "content": "ㅎㅇㅎㅇ",
  "chatType": "SERVER",
  "actionType": "SEND",
  "isDeleted": false,
  "files": [
    {
      "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/chat/53700067-42ba-4011-9db7-8f61aed409d5.png",
      "storeFileName": "chat/53700067-42ba-4011-9db7-8f61aed409d5.png",
      "originalFilename": "테스트이미지 (1).png"
    },
    {
      "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/chat/890def82-799e-4ea1-b8c9-56d6ae260671.png",
      "storeFileName": "chat/890def82-799e-4ea1-b8c9-56d6ae260671.png",
      "originalFilename": "Untitled.png"
    }
  ],
  "createdAt": {
    "$date": "2024-06-06T14:58:20.904Z"
  }
}
```

### 이모지 메시지 collection 
```json
{
  "_id": 59,
  "serverId": 1,
  "channelId": 9,
  "dmId": 0,
  "serverMessageId": 0,
  "forumMessageId": 0,
  "directMessageId": 40,
  "userId": 2,
  "typeId": 9,
  "type": "save"
}
```
