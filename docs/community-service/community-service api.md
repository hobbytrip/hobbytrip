# 커뮤니티 서버

## 메인 화면 읽기

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 메인 화면 읽기 |
| **설명** | 서버, DM 목록을 보내줍니다. |
| **메소드** | `GET` |
| **REST API** | `/api/community/user/{userId}` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        "servers": [
            // 서버 목록 데이터
        ],
        "dms": [
            // DM 목록 데이터
        ]
    }
}
```

## 유저 커뮤니티 회원가입

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 유저 커뮤니티 회원가입 |
| **설명** | 유저 서비스 로그인 하신 뒤 얻은 id값을 커뮤니티 서비스로 전송해주시면 됩니다. |
| **메소드** | `POST` |
| **REST API** | `/api/community/user` |

### Request
```json
{
    "originalId": 4
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 서버 생성

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 생성 |
| **설명** | JWT에 들어있는 유저id 값을 전송해주시면 됩니다. 또한, 프로필 사진은 선택입니다. |
| **메소드** | `POST` |
| **REST API** | `/api/community/server` |

### Request
```javascript
const formdata = new FormData();
formdata.append("userId", userId);
formdata.append("profileImage", profileImage);
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 서버 정보 읽기

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 정보 읽기 |
| **설명** | 서버 초기 채팅 목록, 유저 상태 정보 읽기, 채널, 카테고리, 서버 정보 전송 |
| **메소드** | `GET` |
| **REST API** | `/api/community/server/{serverId}/{userId}` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 서버 초대코드 요청

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 초대코드 요청 |
| **설명** | 응답으로 초대코드를 반환하여 해당 도메인 주소 매핑진행주시면 됩니다. |
| **메소드** | `GET` |
| **REST API** | `/api/community/server/{serverId}/invitation` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        "invitationCode": "초대코드"
    }
}
```

## 서버 초대코드 참가

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 초대코드 참가 |
| **설명** | 초대코드와 함께 서버 id, 유저 id 전송 |
| **메소드** | `POST` |
| **REST API** | `/api/community/server/invite` |

### Request
```json
{
    "invitationCode": "초대코드",
    "serverId": "serverId",
    "userId": "userId"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 서버 참가(오픈 서버)

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 참가(오픈 서버) |
| **설명** | 초대코드 없이 서버 접속(오픈 서버). 만약 오픈 서버가 아닌 경우 초대 코드 없이 접속 불가 |
| **메소드** | `POST` |
| **REST API** | `/api/community/server/open` |

### Request
```json
{
    "serverId": "serverId",
    "userId": "userId"
}
```

### Response
```json
{
    "success": false,
    "code": 1,
    "message": "오픈 서버가 아닙니다."
}
```

## 서버 업데이트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 업데이트 |
| **설명** | 기존 서버 이름, 프로필 이미지 URL이 존재한다면 넣어주셔야 합니다. |
| **메소드** | `PUT` |
| **REST API** | `/api/community/server/{serverId}` |

### Request
```json
{
    "serverId": "serverId",
    "serverName": "serverName",
    "profileImage": "profileImageUrl"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 서버 유저 닉네임 변경

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 유저 닉네임 변경 |
| **설명** | 서버 유저 개인 닉네임을 변경해주는 API 입니다. |
| **메소드** | `PUT` |
| **REST API** | `/api/community/server/{serverId}/user/{userId}/nickname` |

### Request
```json
{
    "nickname": "newNickname"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 서버 프로필 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 프로필 삭제 |
| **설명** | 서버 프로필 삭제 |
| **메소드** | `DELETE` |
| **REST API** | `/api/community/server/{serverId}/profile` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 서버 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 삭제 |
| **설명** | 서버 삭제 API입니다. 서버의 담당자가 아니면 거부됩니다. |
| **메소드** | `DELETE` |
| **REST API** | `/api/community/server/{serverId}` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 서버 내 유저 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 내 유저 삭제 |
| **설명** | 서버 안에 있는 개인 유저 삭제입니다. |
| **메소드** | `DELETE` |
| **REST API** | `/api/community/server/{serverId}/user/{userId}` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 오픈 서버 탑 목록

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 오픈 서버 탑 목록 |
| **설명** | 가장 많은 사용자가 존재하는 인기 오픈 서버 9개를 반환합니다. |
| **메소드** | `GET` |
| **REST API** | `/api/community/servers/top` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        "topServers": [
            // 인기 오픈 서버 목록 데이터
        ]
    }
}
```

## 오픈 서버 이름 검색

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 오픈 서버 이름 검색 |
| **설명** | 페이지 번호는 선택이고 미입력시 0으로 고정됩니다. |
| **메소드** | `GET` |
| **REST API** | `/api/community/server/open/search?name=example&page=0` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        "servers": [
            // 서버 목록 데이터
        ]
    }
}
```

## DM 생성

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 생성 |
| **설명** | 개인 DM을 생성하는 API 입니다. |
| **메소드** | `POST` |
| **REST API** | `/api/community/dm` |

### Request
```json
{
    "userIds": [1, 2]
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## DM 읽기

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 읽기 |
| **설명** | DM 유저 상태 정보, 초기 채팅 목록 |
| **메소드** | `GET` |
| **REST API** | `/api/community/dm/{dmId}` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## DM에 유저 추가

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM에 유저 추가 |
| **설명** | DM에 유저를 추가합니다. |
| **메소드** | `POST` |
| **REST API** | `/api/community/dm/join` |

### Request
```json
{
    "dmId": 1,
    "userIds": [3, 4]
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## DM 이름 업데이트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 이름 업데이트 |
| **설명** | DM 이름을 업데이트 합니다. |
| **메소드** | `PATCH` |
| **REST API** | `/api/community/dm` |

### Request
```json
{
    "dmId": 1,
    "name": "hello"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## DM 프로필 업데이트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 프로필 업데이트 |
| **설명** | DM 프로필을 업데이트 합니다. |
| **메소드** | `PATCH` |
| **REST API** | `/api/community/dm/profile` |

### Request
```json
{
    "dmId": 1,
    "profileImage": "profileImageUrl"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## DM 프로필 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM 프로필 삭제 |
| **설명** | DM 프로필 삭제 |
| **메소드** | `DELETE` |
| **REST API** | `/api/community/dm/profile` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## DM에서 유저 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | DM에서 유저 삭제 |
| **설명** | DM 개인 유저 삭제입니다. (DM 방 나가기) |
| **메소드** | `DELETE` |
| **REST API** | `/api/community/dm/user` |

### Request
```json
{
    "dmId": 1,
    "userId": 1
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 카테고리 생성

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 카테고리 생성 |
| **설명** | 카테고리 생성입니다. |
| **메소드** | `POST` |
| **REST API** | `/api/community/category` |

### Request
```json
{
    "serverId": 1,
    "categoryName": "newCategory"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 카테고리 업데이트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 카테고리 업데이트 |
| **설명** | 카테고리 이름 업데이트입니다. |
| **메소드** | `PATCH` |
| **REST API** | `/api/community/category` |

### Request
```json
{
    "categoryId": 1,
    "categoryName": "updatedCategory"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 카테고리 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 카테고리 삭제 |
| **설명** | 카테고리 삭제입니다. |
| **메소드** | `DELETE` |
| **REST API** | `/api/community/category` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 채널 생성

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 채널 생성 |
| **설명** | 채널 생성입니다. 카테고리 내부에 채널이 존재하는 경우 |
| **메소드** | `POST` |
| **REST API** | `/api/community/channel` |

### Request
```json
{
    "categoryId": 1,
    "channelName": "newChannel"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 채널 업데이트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 채널 업데이트 |
| **설명** | 채널 이름 업데이트입니다. |
| **메소드** | `PATCH` |
| **REST API** | `/api/community/channel` |

### Request
```json
{
    "channelId": 1,
    "channelName": "updatedChannel"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 채널 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 채널 삭제 |
| **설명** | 채널 삭제입니다. |
| **메소드** | `DELETE` |
| **REST API** | `/api/community/channel` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 포럼 생성

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 생성 |
| **설명** | 포럼 생성입니다. 여러 파일 전송이 가능합니다.(우선은 이미지 구현) |
| **메소드** | `POST` |
| **REST API** | `/api/community/forum` |

### Request
```javascript
const formdata = new FormData();
formdata.append("serverId", 1);
formdata.append("channelId", 3);
formdata.append("title", "포럼 제목");
formdata.append("content", "포럼 내용");
formdata.append("files", fileInput.files[0]);
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 포럼 읽기

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 읽기 |
| **설명** | 포럼 읽기입니다. 포럼과 포럼 최초 메시지 반환 |
| **메소드** | `GET` |
| **REST API** | `/api/community/forum/{forumId}` |

### Request
(해당 없음)

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 포럼 업데이트

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 업데이트 |
| **설명** | 포럼 업데이트입니다. 파일 변경 시 파일 아이디 리스트를 보내면 삭제 한 후 새로 업로드합니다. |
| **메소드** | `PATCH` |
| **REST API** | `/api/community/forum` |

### Request
```javascript
const formdata = new FormData();
formdata.append("forumId", 1);
formdata.append("title", "수정된 포럼 제목");
formdata.append("content", "수정된 포럼 내용");
formdata.append("files", fileInput.files[0]);
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```

## 포럼 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 포럼 삭제 |
| **설명** | 포럼 삭제입니다. |
| **메소드** | `DELETE` |
| **REST API** | `/api/community/forum` |

### Request
```json
{
    "forumId": 1,
    "serverId": 1,
    "channelId": 3
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "data": {
        // 응답 데이터
    }
}
```



