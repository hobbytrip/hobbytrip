# 채팅 서버

## DM 구독

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 메시지 구독 경로 |
| **설명** | DM 메시지 구독 경로 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/topic/direct/{directId}` |

### Request
(해당 없음)

### Response
(해당 없음)

---

## 서버 구독

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 길드에 초대되고 수락 누르면 해당 길드 구독(길드번호를) |
| **설명** | 길드에 초대되고 수락 누르면 해당 길드 구독(길드번호를) |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/topic/server/{serverId}` |

### Request
(해당 없음)

### Response
(해당 없음)

---

## 서버 채팅 전송

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 채팅 전송 |
| **설명** | 서버 채팅 전송 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/server/message/send` |

### Request
```json
{
  "serverId": 1,
  "channelId": 4,
  "userId": 1,
  "parentId": 0,
  "profileImage": "ho",
  "writer": "메로나",
  "content": "ㅎㅇㅎㅇ",
  "receiverIds": [1, 2, 3],
  "mentionType": "EVERYONE"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 96,
    "serverId": 1,
    "channelId": 4,
    "userId": 1,
    "parentId": 0,
    "count": 0,
    "profileImage": "ho",
    "writer": "메로나",
    "content": "ㅎㅇㅎㅇ",
    "chatType": "SERVER",
    "actionType": "SEND",
    "files": null,
    "createdAt": "2024-05-17T18:00:04.17766",
    "deleted": false
  }
}
```

---

## 서버 채팅 수정

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 채팅 수정 |
| **설명** | 서버 채팅 수정 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/server/message/modify` |

### Request
```json
{
  "serverId": 1,
  "messageId": 92,
  "content": "수정했습니다.~"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "serverId": 1,
    "messageId": 92,
    "content": "수정했습니다.~",
    "chatType": "SERVER",
    "actionType": "MODIFY",
    "modifiedAt": "2024-05-17T18:00:45.2489124"
  }
}
```

---

## 서버 채팅 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 채팅 삭제 |
| **설명** | 서버 채팅 삭제 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/server/message/delete` |

### Request
```json
{
  "serverId": 1,
  "messageId": 92
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 92,
    "chatType": "SERVER",
    "actionType": "DELETE"
  }
}
```

---

## 서버 채팅 타이핑

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버에서 타이핑 중인지 체크 |
| **설명** | 서버에서 타이핑 중인지 체크 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/server/message/typing` |

### Request
```json
{
  "serverId": 1,
  "channelId": 1,
  "writer": "JohnDoe"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "serverId": 1,
    "channelId": 1,
    "writer": "JohnDoe",
    "chatType": "SERVER",
    "actionType": "TYPING"
  }
}
```

---

## 서버 채널 채팅 목록 조회

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 채널 조회시 채팅 전송(20개) |
| **설명** | 서버 채널 조회시 채팅 전송(20개) |
| **메소드** | `GET` |
| **REST API** | `/api/chat/server/messages/channel?channelId=1&page=0&size=20` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "numberOfElements": 10,
    "totalPages": 1,
    "hasNext": false,
    "data": [
      {
        "messageId": 58,
        "serverId": 1,
        "channelId": 1,
        "userId": 20,
        "parentId": 0,
        "count": 1,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [],
        "createdAt": "2024-05-02T21:04:29.519",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 40,
        "serverId": 1,
        "channelId": 1,
        "userId": 20,
        "parentId": 0,
        "count": 1,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [
          {
            "emojiId": 13,
            "serverId": 1,
            "channelId": 1,
            "dmId": 0,
            "serverMessageId": 40,
            "forumMessageId": 0,
            "directMessageId": 0,
            "userId": 1,
            "typeId": 1,
            "type": "save"
          },
          {
            "emojiId": 14,
            "serverId": 1,
            "channelId": 1,
            "dmId": 0,
            "serverMessageId": 40,
            "forumMessageId": 0,
            "directMessageId": 0,
            "userId": 1,
            "typeId": 1,
            "type": "save"
          }
        ],
        "createdAt": "2024-04-09T20:23:18.926",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 39,
        "serverId": 1,
        "channelId": 1,
        "userId": 20,
        "parentId": 0,
        "count": 4,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-09T20:23:16.692",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 38,
        "serverId": 1,
        "channelId": 1,
        "userId": 20,
        "parentId": 0,
        "count": 0,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-09T20:23:16.524",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 37,
        "serverId": 1,
        "channelId": 1,
        "userId": 20,
        "parentId": 0,
        "count": 0,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-09T20:23:16.013",
        "modifiedAt": null,
        "deleted": false
      }
    ]
  }
}
```

---

## 서버 채널 댓글 목록 조회

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 채팅 메시지에 달린 댓글 조회 |
| **설명** | 서버 채팅 메시지에 달린 댓글 조회 |
| **메소드** | `GET` |
| **REST API** | `/api/chat/server/comments/message?parentId=40&page=0&size=20` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "numberOfElements": 2,
    "totalPages": 1,
    "hasNext": false,
    "data": [
      {
        "messageId": 73,
        "serverId": 1,
        "channelId": 4,
        "userId": 20,
        "parentId": 60,
        "count": 0,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [],
        "createdAt": "2024-05-03T13:32:26.4",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 72,
        "serverId": 1,
        "channelId": 4,
        "userId": 20,
        "parentId": 60,
        "count": 0,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [],
        "createdAt": "2024-05-03T13:24:29.567",
        "modifiedAt": null,
        "deleted": false
      }
    ]
  }
}
```

---

## 서버 커뮤니티 채팅 + 파일 업로드

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 파일을 업로드하는 커뮤니티 채팅 |
| **설명** | 파일을 업로드하는 커뮤니티 채팅 |
| **메소드** | `POST` |
| **REST API** | `/api/chat/server/message/file` |

### Request
```json
{
  "serverId": 1,
  "channelId": 4,
  "userId": 1,
  "parentId": 0,
  "profileImage": "ho",
  "writer": "메로나",
  "content": "ㅎㅇㅎㅇ"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 116,
    "serverId": 1,
    "channelId": 4,
    "userId": 1,
    "parentId": 0,
    "count": 0,
    "profileImage": "ho",
    "writer": "메로나",
    "content": "ㅎㅇㅎㅇ",
    "chatType": "SERVER",
    "actionType": "SEND",
    "files": [
      {
        "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/chatfeb9e7ca-aea6-4a55-bb37-7bbbc42cd894.png",
        "storeFileName": "chatfeb9e7ca-aea6-4a55-bb37-7bbbc42cd894.png",
        "originalFilename": "테스트이미지.png"
      }
    ],
    "createdAt": "2024-05-26T17:30:31.4680672",
    "deleted": false
  }
}
```

---

## DM 채팅 전송

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 메시지 전송 |
| **설명** | DM 메시지 전송 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/direct/message/send` |

### Request
```json
{
  "dmRoomId": 1,
  "userId": 1,
  "parentId": 0,
  "profileImage": "profile.jpg",
  "writer": "JohnDoe",
  "content": "Hello!",
  "receiverIds": [1, 2, 3]
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 51,
    "dmRoomId": 1,
    "userId": 1,
    "parentId": 0,
    "profileImage": "profile.jpg",
    "writer": "JohnDoe",
    "content": "Hello!",
    "chatType": "DM",
    "actionType": "SEND",
    "files": null,
    "createdAt": "2024-05-18T00:35:41.6310431",
    "deleted": false
  }
}
```

---

## DM 채팅 수정

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 채팅 수정 |
| **설명** | DM 채팅 수정 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/direct/message/modify` |

### Request
```json
{
  "dmRoomId": 1,
  "messageId": 4,
  "content": "수정했습니다!"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "dmRoomId": 1,
    "messageId": 4,
    "content": "수정했습니다!",
    "chatType": "DM",
    "actionType": "MODIFY",
    "modifiedAt": "2024-05-18T00:36:00.4925181"
  }
}
```

---

## DM 채팅 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 채팅 삭제 |
| **설명** | DM 채팅 삭제 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/direct/message/delete` |

### Request
```json
{
  "dmRoomId": 1,
  "messageId": 4
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 4,
    "chatType": "DM",
    "actionType": "DELETE"
  }
}
```

---

## DM 채팅 타이핑

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM에서 타이핑 중인지 체크 |
| **설명** | DM에서 타이핑 중인지 체크 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/direct/message/typing` |

### Request
```json
{
  "dmRoomId": 1,
  "writer": "gdgd"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "dmRoomId": 1,
    "writer": "gdgd",
    "chatType": "DM",
    "actionType": "TYPING"
  }
}
```

---

## DM 채팅 목록 조회

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 채팅 조회(20개씩) |
| **설명** | DM 채팅 조회(20개씩) |
| **메소드** | `GET` |
| **REST API** | `/api/chat/direct/messages/room?roomId=1&page=0&size=20` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "numberOfElements": 16,
    "totalPages": 1,
    "hasNext": false,
    "data": [
      {
        "messageId": 47,
        "parentId": 0,
        "dmRoomId": 1,
        "userId": 1,
        "count": 0,
        "profileImage": "profile.jpg",
        "type": "send",
        "writer": "JohnDoe",
        "content": "Hello!",
        "files": null,
        "emojis": [],
        "createdAt": "2024-05-05T18:39:30.773",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 32,
        "parentId": 0,
        "dmRoomId": 1,
        "userId": 1,
        "count": 1,
        "profileImage": "profile.jpg",
        "type": "send",
        "writer": "JohnDoe",
        "content": "Hello!",
        "files": null,
        "emojis": [
          {
            "emojiId": 56,
            "serverId": 1,
            "channelId": 9,
            "dmId": 0,
            "serverMessageId": 0,
            "forumMessageId": 0,
            "directMessageId": 32,
            "userId": 2,
            "typeId": 9,
            "type": "save"
          }
        ],
        "createdAt": "2024-05-03T02:01:24.53",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 11,
        "parentId": 0,
        "dmRoomId": 1,
        "userId": 1,
        "count": 5,
        "profileImage": "profile.jpg",
        "type": "send",
        "writer": "JohnDoe",
        "content": "Hello!",
        "files": null,
        "emojis": [
          {
            "emojiId": 22,
            "serverId": 0,
            "channelId": 0,
            "dmId": 1,
            "serverMessageId": 0,
            "forumMessageId": 0,
            "directMessageId": 11,
            "userId": 1,
            "typeId": 1,
            "type": "save"
          },
          {
            "emojiId": 24,
            "serverId": 0,
            "channelId": 0,
            "dmId": 1,
            "serverMessageId": 0,
            "forumMessageId": 0,
            "directMessageId": 11,
            "userId": 2,
            "typeId": 1,
            "type": "save"
          }
        ],
        "createdAt": "2024-04-11T01:59:45.095",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 8,
        "parentId": 0,
        "dmRoomId": 1,
        "userId": 1,
        "count": 0,
        "profileImage": "profile.jpg",
        "type": "send",
        "writer": "JohnDoe",
        "content": "Hello!",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-11T01:59:44.43",
        "modifiedAt": null,
        "deleted": false
      }
    ]
  }
}
```

---

## DM 댓글 목록 조회

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 채팅 메시지에 달린 댓글 조회 |
| **설명** | DM 채팅 메시지에 달린 댓글 조회 |
| **메소드** | `GET` |
| **REST API** | `/api/chat/direct/comments?parentId=10&page=0&size=20` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "numberOfElements": 5,
    "totalPages": 1,
    "hasNext": false,
    "data": [
      {
        "messageId": 16,
        "parentId": 11,
        "dmRoomId": 1,
        "userId": 2,
        "count": 0,
        "profileImage": "profile.jpg",
        "type": "send",
        "writer": "JohnDoe",
        "content": "Hello!",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-11T02:07:45.499",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 15,
        "parentId": 11,
        "dmRoomId": 1,
        "userId": 2,
        "count": 0,
        "profileImage": "profile.jpg",
        "type": "send",
        "writer": "JohnDoe",
        "content": "Hello!",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-11T02:07:45.214",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 14,
        "parentId": 11,
        "dmRoomId": 1,
        "userId": 2,
        "count": 0,
        "profileImage": "profile.jpg",
        "type": "send",
        "writer": "JohnDoe",
        "content": "Hello!",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-11T02:07:44.799",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 13,
        "parentId": 11,
        "dmRoomId": 1,
        "userId": 2,
        "count": 0,
        "profileImage": "profile.jpg",
        "type": "send",
        "writer": "JohnDoe",
        "content": "Hello!",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-11T02:07:43.172",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 12,
        "parentId": 11,
        "dmRoomId": 1,
        "userId": 2,
        "count": 0,
        "profileImage": "profile.jpg",
        "type": "send",
        "writer": "JohnDoe",
        "content": "Hello!",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-11T02:07:41.093",
        "modifiedAt": null,
        "deleted": false
      }
    ]
  }
}
```

---

## DM 채팅 + 파일 업로드

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 파일을 업로드하는 다이렉트 채팅 |
| **설명** | 파일을 업로드하는 다이렉트 채팅 |
| **메소드** | `POST` |
| **REST API** | `/api/chat/direct/message/file` |

### Request
```json
{
  "dmRoomId": 1,
  "userId": 1,
  "parentId": 0,
  "profileImage": "profile.jpg",
  "writer": "JohnDoe",
  "content": "Hello!"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 55,
    "dmRoomId": 1,
    "userId": 1,
    "parentId": 0,
    "profileImage": "profile.jpg",
    "writer": "JohnDoe",
    "content": "Hello!",
    "chatType": "DM",
    "actionType": "SEND",
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
    "createdAt": "2024-05-26T17:45:17.4768141",
    "deleted": false
  }
}
```

---

## 포럼 채팅 전송

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 채팅 전송 |
| **설명** | 포럼 채팅 전송 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/forum/message/send` |

### Request
```json
{
  "serverId": 1,
  "forumId": 2,
  "channelId": 9,
  "userId": 25,
  "parentId": 0,
  "profileImage": "ho",
  "writer": "바밤바",
  "content": "댓글",
  "forumCategory": "TODAY"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 56,
    "forumId": 2,
    "serverId": 1,
    "channelId": 9,
    "userId": 25,
    "parentId": 0,
    "count": 0,
    "profileImage": "ho",
    "writer": "바밤바",
    "content": "댓글",
    "chatType": "FORUM",
    "actionType": "SEND",
    "forumCategory": "TODAY",
    "files": null,
    "createdAt": "2024-05-18T01:15:41.5502819",
    "deleted": false
  }
}
```

---

## 포럼 채팅 수정

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 채팅 수정 |
| **설명** | 포럼 채팅 수정 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/forum/message/modify` |

### Request
```json
{
  "serverId": 1,
  "messageId": 56,
  "content": "수정했습니다!"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 56,
    "serverId": 1,
    "forumId": 2,
    "content": "수정했습니다!",
    "chatType": "FORUM",
    "actionType": "MODIFY",
    "modifiedAt": "2024-05-18T01:16:55.1900265"
  }
}
```

---

## 포럼 채팅 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 채팅 삭제 |
| **설명** | 포럼 채팅 삭제 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/forum/message/delete` |

### Request
```json
{
  "serverId": 1,
  "messageId": 56
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 56,
    "chatType": "FORUM",
    "actionType": "DELETE"
  }
}
```

---

## 포럼 채팅 타이핑

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼에서 타이핑 중인지 체크 |
| **설명** | 포럼에서 타이핑 중인지 체크 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/forum/message/typing` |

### Request
```json
{
  "serverId": 1,
  "forumId": 1,
  "writer": "JohnDoe"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "serverId": 1,
    "forumId": 1,
    "writer": "JohnDoe",
    "chatType": "FORUM",
    "actionType": "TYPING"
  }
}
```

---

## 포럼 채널 채팅 목록 조회

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 조회시 채팅 전송(20개) |
| **설명** | 포럼 조회시 채팅 전송(20개) |
| **메소드** | `GET` |
| **REST API** | `/api/chat/forum/messages/forum?forumId=1&page=0&size=20` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "numberOfElements": 19,
    "totalPages": 1,
    "hasNext": false,
    "data": [
      {
        "messageId": 53,
        "forumId": 2,
        "serverId": 1,
        "channelId": 9,
        "userId": 25,
        "parentId": 0,
        "count": 0,
        "profileImage": "ho",
        "type": "send",
        "writer": "바밤바",
        "content": "댓글",
        "files": null,
        "emojis": [],
        "createdAt": "2024-05-05T18:44:54.377",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 19,
        "forumId": 2,
        "serverId": 1,
        "channelId": 9,
        "userId": 25,
        "parentId": 0,
        "count": 8,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [
          {
            "emojiId": 29,
            "serverId": 1,
            "channelId": 9,
            "dmId": 0,
            "serverMessageId": 0,
            "forumMessageId": 19,
            "directMessageId": 0,
            "userId": 2,
            "typeId": 9,
            "type": "save"
          }
        ],
        "createdAt": "2024-04-16T00:45:18.627",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 18,
        "forumId": 2,
        "serverId": 1,
        "channelId": 9,
        "userId": 25,
        "parentId": 0,
        "count": 2,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [
          {
            "emojiId": 35,
            "serverId": 1,
            "channelId": 9,
            "dmId": 0,
            "serverMessageId": 0,
            "forumMessageId": 18,
            "directMessageId": 0,
            "userId": 2,
            "typeId": 9,
            "type": "save"
          },
          {
            "emojiId": 36,
            "serverId": 1,
            "channelId": 9,
            "dmId": 0,
            "serverMessageId": 0,
            "forumMessageId": 18,
            "directMessageId": 0,
            "userId": 2,
            "typeId": 9,
            "type": "save"
          }
        ],
        "createdAt": "2024-04-16T00:45:17.955",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 16,
        "forumId": 2,
        "serverId": 1,
        "channelId": 9,
        "userId": 25,
        "parentId": 0,
        "count": 7,
        "profileImage": "ho",
        "type": "send",
        "writer": "메로나",
        "content": "ㅎㅇㅎㅇ",
        "files": null,
        "emojis": [
          {
            "emojiId": 38,
            "serverId": 1,
            "channelId": 9,
            "dmId": 0,
            "serverMessageId": 0,
            "forumMessageId": 16,
            "directMessageId": 0,
            "userId": 2,
            "typeId": 9,
            "type": "save"
          }
        ],
        "createdAt": "2024-04-16T00:40:36.693",
        "modifiedAt": null,
        "deleted": false
      }
    ]
  }
}
```

---

## 포럼 채널 댓글 목록 조회

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 채팅 메시지에 달린 댓글 조회 |
| **설명** | 포럼 채팅 메시지에 달린 댓글 조회 |
| **메소드** | `GET` |
| **REST API** | `/api/chat/forum/comments/message?parentId=40&page=0&size=20` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "numberOfElements": 8,
    "totalPages": 1,
    "hasNext": false,
    "data": [
      {
        "messageId": 36,
        "forumId": 2,
        "serverId": 1,
        "channelId": 9,
        "userId": 25,
        "parentId": 19,
        "count": 0,
        "profileImage": "ho",
        "type": "modify",
        "writer": "바밤바",
        "content": "수정했습니다!",
        "files": null,
        "emojis": [],
        "createdAt": "2024-05-03T01:20:02.243",
        "modifiedAt": "2024-05-03T01:20:30.31",
        "deleted": false
      },
      {
        "messageId": 26,
        "forumId": 2,
        "serverId": 1,
        "channelId": 9,
        "userId": 25,
        "parentId": 19,
        "count": 0,
        "profileImage": "ho",
        "type": "send",
        "writer": "바밤바",
        "content": "댓글",
        "files": null,
        "emojis": [
          {
            "emojiId": 43,
            "serverId": 1,
            "channelId": 9,
            "dmId": 0,
            "serverMessageId": 0,
            "forumMessageId": 26,
            "directMessageId": 0,
            "userId": 2,
            "typeId": 9,
            "type": "save"
          }
        ],
        "createdAt": "2024-04-16T00:46:00.282",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 25,
        "forumId": 2,
        "serverId": 1,
        "channelId": 9,
        "userId": 25,
        "parentId": 19,
        "count": 0,
        "profileImage": "ho",
        "type": "send",
        "writer": "바밤바",
        "content": "댓글",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-16T00:46:00.028",
        "modifiedAt": null,
        "deleted": false
      },
      {
        "messageId": 24,
        "forumId": 2,
        "serverId": 1,
        "channelId": 9,
        "userId": 25,
        "parentId": 19,
        "count": 0,
        "profileImage": "ho",
        "type": "send",
        "writer": "바밤바",
        "content": "댓글",
        "files": null,
        "emojis": [],
        "createdAt": "2024-04-16T00:45:59.88",
        "modifiedAt": null,
        "deleted": false
      }
    ]
  }
}
```

---

## 포럼 채팅 + 파일 업로드

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 파일을 업로드하는 포럼 채팅 |
| **설명** | 파일을 업로드하는 포럼 채팅 |
| **메소드** | `POST` |
| **REST API** | `/api/chat/forum/message/file` |

### Request
```json
{
  "serverId": 1,
  "forumId": 2,
  "channelId": 9,
  "userId": 25,
  "parentId": 0,
  "profileImage": "ho",
  "writer": "바밤바",
  "content": "댓글"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "messageId": 76,
    "forumId": 2,
    "serverId": 1,
    "channelId": 9,
    "userId": 25,
    "parentId": 0,
    "count": 0,
    "profileImage": "ho",
    "writer": "바밤바",
    "content": "댓글",
    "chatType": "FORUM",
    "actionType": "SEND",
    "files": [
      {
        "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/chat/4c209438-faff-437d-a687-d8aaebc79476.png",
        "storeFileName": "chat/4c209438-faff-437d-a687-d8aaebc79476.png",
        "originalFilename": "bd476f6e-b893-4402-8999-749031421f13.png"
      },
      {
        "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/chat/998eabcd-f1a5-4ced-a4f5-32912a3a9142.png",
        "storeFileName": "chat/998eabcd-f1a5-4ced-a4f5-32912a3a9142.png",
        "originalFilename": "테스트이미지.png"
      },
      {
        "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/chat/98ce7c29-2306-4200-962e-dc75c378d932.png",
        "storeFileName": "chat/98ce7c29-2306-4200-962e-dc75c378d932.png",
        "originalFilename": "bd476f6e-b893-4402-8999-749031421f13 (1).png"
      }
    ],
    "createdAt": "2024-05-26T17:46:04.1305033",
    "deleted": false
  }
}
```

---

## 이모지 저장

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 이모지 저장 |
| **설명** | 이모지 저장 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/emoji/save` |

### Request
(해당 없음)

### Response
(해당 없음)

---

## 이모지 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 이모지 삭제 |
| **설명** | 이모지 삭제 |
| **메소드** | `WEBSOCKET` |
| **REST API** | `/ws/chat/emoji/delete` |

### Request
(해당 없음)

### Response
(해당 없음)

---

## 유저 텍스트 채널 위치 저장

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 유저 텍스트 채널 위치 저장 |
| **설명** | 반환값은 없고 요청값만 존재 |
| **메소드** | `POST` |
| **REST API** | `/server/user/location` |

### Request
(해당 없음)

### Response
(해당 없음)

---

## 포럼 채팅 개수 조회

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 별로 포럼에 달린 채팅 개수를 조회 |
| **설명** | 포럼 별로 포럼에 달린 채팅 개수를 조회 |
| **메소드** | `GET` |
| **REST API** | `/feign/forum/messages/count` |

### Request
(해당 없음)

### Response
(해당 없음)

---

## 예외처리

| 항목 | 내용 |
|---|---|
| **설계 완료** | No |

---

## 커뮤니티 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |

---

## 카테고리 있는 채널 생성 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | createChannelWithCategory |
| **설명** | createChannelWithCategory |
| **메소드** | `POST` |
| **REST API** | `http://localhost:8080/channel` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "channel-create",
    "serverId": 1,
    "userId": null,
    "channelId": 4,
    "categoryId": 1,
    "channelType": "CHAT",
    "name": "테스트 채널"
  }
}
```

---

## 카테고리 없는 채널 생성 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | createChannelWithoutCategory |
| **설명** | createChannelWithoutCategory |
| **메소드** | `POST` |
| **REST API** | `http://localhost:8080/channel` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "channel-create",
    "serverId": 1,
    "userId": null,
    "channelId": 5,
    "categoryId": 1,
    "channelType": "CHAT",
    "name": "테스트 채널"
  }
}
```

---

## 서버 업데이트 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | updateServerWithoutOpen |
| **설명** | updateServerWithoutOpen |
| **메소드** | `PATCH` |
| **REST API** | `http://localhost:8080/server` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "server-update",
    "serverId": 1,
    "profile": "http.image.png",
    "name": "example2"
  }
}
```

---

## 서버 삭제 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | deleteServer |
| **설명** | deleteServer |
| **메소드** | `DELETE` |
| **REST API** | `http://localhost:8080/server` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "server-delete",
    "serverId": 3,
    "profile": null,
    "name": "example"
  }
}
```

---

## DM 업데이트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | updateDm |
| **설명** | updateDm |
| **메소드** | `PATCH` |
| **REST API** | `http://localhost:8080/dm` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "dm-update",
    "dmId": 1,
    "name": "hello",
    "profile": null
  }
}
```

---

## 채널 생성 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | createChannelWithCategory |
| **설명** | createChannelWithCategory |
| **메소드** | `POST` |
| **REST API** | `http://localhost:8080/channel` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "channel-create",
    "serverId": 1,
    "userId": null,
    "channelId": 3,
    "categoryId": 1,
    "channelType": "CHAT",
    "name": "테스트 채널"
  }
}
```

---

## 채널 업데이트 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | updateChannel |
| **설명** | updateChannel |
| **메소드** | `PATCH` |
| **REST API** | `http://localhost:8080/channel` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "channel-update",
    "serverId": 1,
    "userId": null,
    "channelId": 1,
    "categoryId": 1,
    "channelType": "CHAT",
    "name": "테스트 업데이트 채널"
  }
}
```

---

## 채널 삭제 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | deleteChannel |
| **설명** | deleteChannel |
| **메소드** | `DELETE` |
| **REST API** | `http://localhost:8080/channel` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "channel-delete",
    "serverId": 1,
    "userId": null,
    "channelId": 1,
    "categoryId": 1,
    "channelType": "CHAT",
    "name": "테스트 업데이트 채널"
  }
}
```

---

## 포럼 생성 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | createForum |
| **설명** | createForum |
| **메소드** | `POST` |
| **REST API** | `http://localhost:8080/forum` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "forum-create",
    "serverId": 1,
    "forumId": 1,
    "channelId": 14,
    "title": "테스트 포럼3",
    "writer": "abc",
    "content": "테스트 포럼3",
    "createAt": "2024-05-20T19:55:16.946008567",
    "updateAt": "2024-05-20T19:55:16.946008567",
    "files": [
      {
        "fileId": 1,
        "fileUrl": "http://image.png"
      },
      {
        "fileId": 2,
        "fileUrl": "http://image.png"
      }
    ]
  }
}
```

---

## 포럼 업데이트 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | updateForum |
| **설명** | updateForum |
| **메소드** | `PATCH` |
| **REST API** | `http://localhost:8080/forum` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "forum-create",
    "serverId": 1,
    "forumId": 1,
    "channelId": 14,
    "title": "테스트 포럼3",
    "writer": "abc",
    "content": "테스트 포럼3",
    "createAt": "2024-05-20T19:55:16.946008567",
    "updateAt": "2024-05-20T19:55:16.946008567",
    "files": [
      {
        "fileId": 1,
        "fileUrl": "http://image.png"
      },
      {
        "fileId": 2,
        "fileUrl": "http://image.png"
      }
    ]
  }
}
```

---

## 포럼 삭제 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | deleteForum |
| **설명** | deleteForum |
| **메소드** | `DELETE` |
| **REST API** | `http://localhost:8080/forum` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "forum-delete",
    "serverId": 1,
    "forumId": 2,
    "channelId": 14,
    "title": null,
    "writer": null,
    "content": null,
    "createAt": null,
    "updateAt": null,
    "files": null
  }
}
```

---

## 카테고리 생성 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | createCategory |
| **설명** | createCategory |
| **메소드** | `POST` |
| **REST API** | `http://localhost:8080/category` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "category-create",
    "serverId": 1,
    "categoryId": 9,
    "name": "채팅채널5"
  }
}
```

---

## 카테고리 업데이트 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | updateCategory |
| **설명** | updateCategory |
| **메소드** | `PATCH` |
| **REST API** | `http://localhost:8080/category` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "category-update",
    "serverId": 1,
    "categoryId": 9,
    "name": "채팅채널12"
  }
}
```

---

## 카테고리 삭제 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | deleteCategory |
| **설명** | deleteCategory |
| **메소드** | `DELETE` |
| **REST API** | `http://localhost:8080/category` |

### Request
(해당 없음)

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "type": "category-delete",
    "serverId": 1,
    "categoryId": 9,
    "name": "채팅채널12"
  }
}
```

---

## 음성 채널 입/퇴장 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | No |

---

## 음성 채널 입장 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 음성 채널 입장 이벤트 |
| **설명** | 음성 채널 입장 이벤트 |
| **메소드** | `POST` |
| **REST API** | `http://localhost:5000/api/sessions/SessionA/connections` |

### Request
```json
{
  "userId": 1,
  "serverId": 1,
  "channelId": 1,
  "voiceConnectionState": "VOICE_JOIN"
}
```

### Response
(해당 없음)

---

## 음성 채널 퇴장 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 음성 채널 퇴장 이벤트 |
| **설명** | 음성 채널 퇴장 이벤트 |
| **메소드** | `DELETE` |
| **REST API** | `http://localhost:5000/api/sessions/SessionA/disconnect` |

### Request
```json
{
  "userId": 1,
  "serverId": 1,
  "channelId": 1,
  "voiceConnectionState": "VOICE_LEAVE"
}
```

### Response
(해당 없음)

---

## 온/오프 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | No |

---

## 유저 접속 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 유저 접속 이벤트 |
| **설명** | 유저 접속 이벤트 |
| **메소드** | `WEBSOCKET` |
| **REST API** | (해당 없음) |

### Request
```json
{
  "userId": 1,
  "type": "CONNECT",
  "state": "ONLINE"
}
```

### Response
(해당 없음)

---

## 유저 접속 끊김 이벤트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 유저 접속 끊김 이벤트 |
| **설명** | 유저 접속 끊김 이벤트 |
| **메소드** | `WEBSOCKET` |
| **REST API** | (해당 없음) |

### Request
```json
{
  "userId": 1,
  "type": "DISCONNECT",
  "state": "OFFLINE"
}
```

### Response
(해당 없음)


