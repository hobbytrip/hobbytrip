# 미디어 시그널링 서버
<img width="1169" alt="스크린샷 2024-06-20 19 16 55" src="https://github.com/hobbytrip/hobbytrip/assets/75834815/c2651d10-3f48-49b8-b888-7e44a67ebb34">

## 세션 생성

| 항목 | 내용 |
|---|---|
| **기능** | 세션 생성 |
| **설명** | 세션 생성 |
| **메소드** | `POST` |
| **REST API** | `/api/sessions` |

### Request
```json
{
  "type": "video",
  "title": "Sample Session"
}
```

### Response
```json
{
  "sessionId": "sessionId",
  "createdAt": "timestamp"
}
```

## 통화 참여

| 항목 | 내용 |
|---|---|
| **기능** | 통화 참여 |
| **설명** | 커넥션 생성 |
| **메소드** | `POST` |
| **REST API** | `/api/sessions/{sessionId}/connections` |

### Request
```json
{
  "userId": "userId",
  "channelId": "channelId",
  "serverId": "serverId"
}
```

### Response
```plaintext
ws://fittrip.site:5443/sessionId=sessionId&token=tok_token
```

## 통화 퇴장

| 항목 | 내용 |
|---|---|
| **기능** | 통화 퇴장 |
| **설명** | 퇴장 |
| **메소드** | `DELETE` |
| **REST API** | `/api/sessions/newSessionId/disconnect` |

### Request
```json
{
  "userId": "userId",
  "sessionId": "sessionId"
}
```

### Response
```json
{
  "status": "success",
  "message": "User has left the session"
}
```
