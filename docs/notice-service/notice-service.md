# 알림 서버
<img width="1212" alt="스크린샷 2024-06-20 19 29 38" src="https://github.com/hobbytrip/hobbytrip/assets/75834815/8e069b52-3f3c-4fda-92f7-cc0d3a16fa1f">


## DM 알림

| 항목 | 내용 |
|---|---|
| **기능** | DM 알림 |
| **설명** | sse 연결 된 클라이언트쪽으로 실시간 알림 전송, 미수신한 알림을 확인하기 위한 Last-Event-ID존재 헤더가 들어올 수 있고, 들어오지 않을 수 있음 |
| **메소드** | `GET` |
| **REST API** | `api/notice/dm/subscribe` |

### Request
```json
"sse endpoint: api/notice/dm/subscribe"
"RequestHeade: Last-Event-ID"
```

### Response
```json
{
  "알림 주는 유저 ID" : "userID"
  "DmRoomId": "DmRoomId"
  "AlarmTyep: AlarmTyep(enum: DM(DM-ALARM), SERVER(SERVER-ALARM))"
  "Content": "Content"
  "알림 받는 유저(들)": "ReceiverIds"
  "Read" : "isRead (True/false)"
}
```

## Server 알림

| 항목 | 내용 |
|---|---|
| **기능** | Server 알림 |
| **설명** | sse 연결 된 클라이언트쪽으로 실시간 알림 전송, 미수신한 알림을 확인하기 위한 Last-Event-ID존재 헤더가 들어올 수 있고, 들어오지 않을 수 있음 |
| **메소드** | `GET` |
| **REST API** | `api/notice/Server/subscribe` |

### Request
```json
"sse endpoint: api/notice/Server/subscribe"
"RequestHeade: Last-Event-ID"
```

### Response
```json
{
  "알림 주는 유저 ID" : "userID"
  "ServerId": "ServerId"
  "AlarmTyep: AlarmTyep(enum: DM(DM-ALARM), SERVER(SERVER-ALARM)"
  "Content": "Content"
  "알림 받는 유저(들)": "ReceiverIds"
  "Mention : MentionType (enum: EVERYONE(MENTION-EVERYONE), HERE(MENTION-HERE), USER(MENTION-USER)"
  "Read" : "isRead (True/false)"
}
```

## dm 알림 확인

| 항목 | 내용 |
|---|---|
| **기능** | dm 알림 읽음처리 |
| **설명** | dm방에 들어가는 순간 알림을 읽음 처리 하기 때문에 delete 요청을 서버쪽에 보내줍니다. |
| **메소드** | `DELTE` |
| **REST API** | `api/notice/dmRoom/{dmRoomId}` |

### Request
```json
"PathVariable": "dmRoomId(현재 들어간 dm 방 ID)"
"ReqeustParam": "userId"
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "True,False"
}
```
## server 알림 확인

| 항목 | 내용 |
|---|---|
| **기능** | server 알림 읽음 처리 |
| **설명** | server에 들어가는 순간 알림을 읽음 처리 하기 때문에 delete 요청을 서버쪽에 보내줍니다. |
| **메소드** | `DELTE` |
| **REST API** | `api/notice/server/{serverId}` |

### Request
```json
"PathVariable": "serverId(현재 들어간 server 방 ID)"
"ReqeustParam": "userId"
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "True,False"
}
```

## server 알림 확인

| 항목 | 내용 |
|---|---|
| **기능** | server 알림 확인 |
| **설명** | server에 들어가는 순간 알림을 읽음 처리 하기 때문에 delete 요청을 서버쪽에 보내줍니다. |
| **메소드** | `DELTE` |
| **REST API** | `api/notice/server/{serverId}` |

### Request
```json
"PathVariable": "serverId(현재 들어간 server 방 ID)"
"ReqeustParam": "userId"
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "True,False"
}
```

## dm 알림 확인

| 항목 | 내용 |
|---|---|
| **기능** | dm 알림 확인 |
| **설명** | Main View에 알림 띄우기 위한 요청 (디엠방에 읽지 않은 알림이 있으면 빨간불떠야하는 기능) |
| **메소드** | `GET` |
| **REST API** | `api/notice/dm/dmRoomIds` |

### Request
```json
"ReqeustParam": "userId"
"Header" : "Authorization: Bearer {access_token}"
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "True,False"
}
```

## server 알림 확인

| 항목 | 내용 |
|---|---|
| **기능** | server 알림 확인 |
| **설명** | Main View에 알림 띄우기 위한 요청 (서버에 읽지 않은 알림이 있으면 빨간불떠야하는 기능) |
| **메소드** | `GET` |
| **REST API** | `api/notice/server/serverIds` |

### Request
```json
"ReqeustParam": "userId"
"Header" : "Authorization: Bearer {access_token}"
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "True,False"
}
```
