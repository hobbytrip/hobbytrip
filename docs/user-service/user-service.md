# 유저 서비스 API 명세서
<img width="1173" alt="스크린샷 2024-06-20 19 15 44" src="https://github.com/hobbytrip/hobbytrip/assets/75834815/6909766c-28aa-46bc-ad97-732d013c9861">

## 로그인

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 로그인 |
| **설명** | 유저 로그인 기능 |
| **메소드** | `POST` |
| **REST API** | `api/user/login` |

### Request
```json
{
  "email": "사용자 이메일",
  "password": "비밀번호"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "email": "사용자 이메일",
    "password": "비밀번호"
  }
}
```

## 회원가입

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 회원가입 |
| **설명** | 유저 회원가입 기능 |
| **메소드** | `POST` |
| **REST API** | `api/user/signup` |

### Request
```json
{
  "email": "사용자 이메일",
  "nickname": "닉네임",
  "username": "사용자명",
  "password": "비밀번호",
  "birthdate": "생년월일",
  "notificationEnabled": "알림 설정 (true/false)"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "userId": "생성된 사용자 ID",
    "email": "사용자 이메일",
    "nickname": "닉네임",
    "username": "사용자명",
    "birthdate": "생년월일",
    "notificationEnabled": "알림 설정(true, false)",
    "createAt": "회원가입 날짜"
  }
}
```


## 로그아웃

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 로그아웃 |
| **설명** | 시큐리티와 logout 경로가 겹치면서 user-logout으로 경로 수정 |
| **메소드** | `POST` |
| **REST API** | `api/user/user-logout` |

### Request
```json
{
  "accesstoken": "accesstoken",
  "refreshtoken": "refreshtoken"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": true
}
```
## 회원탈퇴

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 회원탈퇴 |
| **설명** | 유저 회원 탈퇴 기능 유저 데이터 완전히 삭제 |
| **메소드** | `DELETE` |
| **REST API** | `/api/user/delete` |

### Request
```json
{
  "email" : "이메일",
  "password" : "비밀 번호"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": true
}
```
## 토큰 재발급

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 토큰 재발급 |
| **설명** | 토큰 만료시 재발급 받는 기능 |
| **메소드** | `POST` |
| **REST API** | `/api/user/reissue` |

### Request
```json
{
  "accessToken": "엑세스 토큰",
  "refreshToken": "리프레쉬 토큰"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "accessToken": "엑세스 토큰",
    "refreshToken": "리프레쉬 토큰"
  }
}
```

## 사용자 프로필 조회

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 사용자 프로필 조회 |
| **설명** | 사용자 본인 계정 프로필 조회 |
| **메소드** | `POST` |
| **REST API** | `/api/user/profile` |

### Request
```json
"Header: Authorization: Bearer {access_token}"
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "id": "사용자 ID",
    "name": "사용자명",
    "nickname": "별명",
    "email": "이메일@example.com",
    "profileImage": "프로필 이미지 경로",
    "phone": "010-1234-2123",
    "statusMessage": "상태 메시지",
    "modifiedAt": "2023-03-29T15:00:00Z"
  }
}
```

## 프로필 사진 수정

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 프로필 사진 수정 |
| **설명** | 사용자 본인 프로필 사진 수정 기능 |
| **메소드** | `POST` |
| **REST API** | `/api/user/profile/image` |

### Request
```json
"Header : Authorization: Bearer {access_token}"
"Parameter : image: 업로드 할 이미지 파일 (Form Data)"
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "profileImage": "프로필 사진 URL",
    "modifiedAt": "수정 시간"
  }
}
```

## 프로필 사진 삭제

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 프로필 사진 삭제 |
| **설명** | 사용자 프로필 삭제, 기본 이미지로 변경됨 |
| **메소드** | `GET` |
| **REST API** | `/api/user/profile/image?delete={이미지 경로}` |

### Request
```json
"Header : Authorization: Bearer {access_token}"
"Parameter : key: delete value: 업로드 할 이미지 파일 ( Quary parameter )"
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": true
}
```

## 별명 수정(닉네임)

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 별명 수정(닉네임) |
| **설명** | 사용자 본인 프로필 별명 수정 기능 |
| **메소드** | `PATCH` |
| **REST API** | `/api/user/profile/nickname` |

### Request
```json
{
  "nickname": "새로운 닉네임"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "nickName": "새로운 닉네임",
    "modifiedAt": "2024-04-15T19:46:25.355216"
  }
}
```

## 소개 수정

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 소개 수정 |
| **설명** | 사용자 본인 프로필 소개 수정 기능 |
| **메소드** | `PATCH` |
| **REST API** | `/api/user/profile/statusmessage` |

### Request
```json
{
  "statusMessage": "프로필 상태메시지 내용"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "userId" : "유저 아이디",
    "statusMessage": "프로필 상태메시지 변경 내용",
    "modifiedAt" : "변경 시간"
  }
}
```
## 알림 설정

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 알림 설정 |
| **설명** | 사용자 알림 설정 |
| **메소드** | `PATCH` |
| **REST API** | `/api/user/profile/notification` |

### Request
```json
{
  "notice": "true/false"
}
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": {
    "userId" : "사용자 아이디",
    "notice" : "true/false"
  }
}
```

## 친구 대기 중인 친구 목록

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 친구 대기 중인 친구 목록 |
| **설명** | 대기 중인 친구 목록 조회 기능 |
| **메소드** | `GET` |
| **REST API** | `/api/user/friends/received` |

### Request
```json
"Header: Authorization: Bearer {access_token}"
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": [
    {
      "friendshipId": "친구요청ID(친구ID 아니고, 요청ID)",
      "friendEmail": "친구 이메일",
      "friendName": "친구 이름",
      "status": "대기 상태(WAITTING)",
      "imageUrl": "이미지 링크"
    }
  ]
}
```

## 친구 목록 (온 & 오프라인)

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 친구 목록 (온 & 오프라인) |
| **설명** | 친구 목록 조회 기능, 온/오프라인 친구 확인 가능 |
| **메소드** | `GET` |
| **REST API** | `/api/user/friends/friendList` |

### Request
```json
"Header: Authorization: Bearer {access_token}"
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": [
    {
      "userId": "현재로그인된유저",
      "friendId": "친구 Id(friend.getUserId())",
      "friendImageUrl": "친구의 프로필 링크",
      "friendName": "친구 이름",
      "connectionState" : "(enum: ONLINE, OFFLINE)"
    },
     {
      "userId": "현재로그인된유저",
      "friendId": "친구 Id(friend.getUserId())",
      "friendImageUrl": "친구의 프로필 링크",
      "friendName": "친구 이름",
      "connectionState" : "(enum: ONLINE, OFFLINE)"
    }
  ]
}
```

## 친구 추가

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 친구 추가 |
| **설명** | 친구를 맺고자 하는 사용자에게 요청 전송 |
| **메소드** | `POST` |
| **REST API** | `/api/user/friends/{email}` |

### Request
```json
"PathVariable: email : 요청 보낼 친구의 이메일"
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": "ok"
}
```

## 친구 삭제 및 거절

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 친구 삭제 및 거절 |
| **설명** | 친구의 ID 값으로 친구 삭제 및 거절 |
| **메소드** | `DELETE` |
| **REST API** | `/api/user/friends/delete/{friendshipId}` |

### Request
```json
"PathVariable: friendId : 친구 Id"
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": "ok"
}
```

## 친구 요청 수락

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 친구 요청 수락 |
| **설명** | 친구 요청의 ID 값으로 친구 요청 수락 |
| **메소드** | `POST` |
| **REST API** | `/api/user/friends/approve/{friendshipId}` |

### Request
```json
"PathVariable: friendshipId : 친구 요청 Id"
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": "ok"
}
```

## 친구요청 있는 지 확인

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 친구요청 있는 지 확인 |
| **설명** | main에서 현재 와 있는 친구 대기 요청이 있는 지 확인 |
| **메소드** | `GET` |
| **REST API** | `/api/user/friends/checkFriendship` |

### Request
```json
"Header: Authorization: Bearer {access_token}"
```

### Response
```json
{
  "success": true,
  "code": 0,
  "message": "Ok",
  "data": "true / false // 요청이 있으면 true, 없으면 fasle"
}
```
