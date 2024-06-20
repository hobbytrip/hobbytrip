# 미디어 시그널링 서버

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
