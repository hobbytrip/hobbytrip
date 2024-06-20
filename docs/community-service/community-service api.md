# 커뮤니티 서버
<img width="1051" alt="스크린샷 2024-06-20 19 22 03" src="https://github.com/hobbytrip/hobbytrip/assets/75834815/6508ca88-cf57-4098-a087-708935bc0545">


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
            {
                "serverId": 1,
                "name": "example",
                "profile": null
            },
            {
                "serverId": 2,
                "name": "example",
                "profile": null
            },
            {
                "serverId": 3,
                "name": "example",
                "profile": null
            }
        ],
        "dms": [
             {
                "dmId": 1,
                "name": "abc, abc",
                "profile": null
            },
            {
                "dmId": 2,
                "name": "abc, abc",
                "profile": null
            },
            {
                "dmId": 3,
                "name": "abc, abc",
                "profile": null
            },
            {   "success": true,    
			          "code": 0,    
			          "message": "Ok",    
			          "data": {"servers": [{"serverId": 1,
			                          "name": "example",
			                          "profile": null},
			                          {"serverId": 2,
				                          "name": "example","profile": null},            
				                         {"serverId": 3,"name": "example""profile": null}],        
				                         "dms": [{"dmId": 1, "name": "abc, abc","profile": null }, 
				                         { "dmId": 2,"name": "abc, abc", "profile": null },            
				                         {"dmId": 3, "name": "abc, abc", "profile": null},            
								                {"dmId": 4, "name": "abc, abc","profile": null
            }
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
        "id": 1,
        "originalId": 4,
        "email": "abc4@naver.com",
        "name": "abc",
        "profile": "http://image.png"
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
        "serverId": 1,
        "managerId": 4,
        "profile": null,
        "description": null,
        "name": "example",
        "open": false
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
         "server": {
            "serverId": 1,
            "managerId": 1,
            "profile": null,
            "description": null,
            "name": "example",
            "open": false
        },
        "serverUserInfos": [
            {
                "userId": 1,
                "name": "test123"
            }
        ],
        "categories": [
            {
                "categoryId": 1,
                "name": "채팅 채널"
            },
            {
                "categoryId": 2,
                "name": "음성 채널"
            }
        ],
        "channels": [
            {
                "channelId": 1,
                "categoryId": 1,
                "channelType": "CHAT",
                "name": "일반"
            },
            {
                "channelId": 2,
                "categoryId": 2,
                "channelType": "VOICE",
                "name": "일반"
            },
            {
                "channelId": 3,
                "categoryId": null,
                "channelType": "FORUM",
                "name": "테스트 포럼"
            }
        ],
        "usersState": null,
        "messages": null
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
        "serverId": 1,
        "managerId": 1,
        "profile": null,
        "description": null,
        "name": "example",
        "open": false
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
        "serverId": 1,
        "managerId": 4,
        "profile": null,
        "description": null,
        "name": "example",
        "open": true
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
        "serverId": 1,
        "userId": 2,
        "name": "abcd"
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
        "serverId": 1,
        "managerId": 1,
        "profile": null,
        "description": null,
        "name": "example",
        "open": false
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
```json
{
    "serverId" : 1,
    "userId" : 4
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "Server delete success!!"
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
```json
{
    "userId" : 1,
    "serverId" : 1,
    "serverUserId" : 2
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "ServerUser delete success!!"
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
    "message": "Ok",
    "data": [
        {
            "serverId": 1,
            "managerId": 1,
            "profile": "image.png",
            "description": null,
            "open": true,
            "userCount": 3
        },
        {
            "serverId": 11,
            "managerId": 1,
            "profile": "image.png",
            "description": null,
            "open": true,
            "userCount": 1
        },
        {
            "serverId": 10,
            "managerId": 1,
            "profile": "image.png",
            "description": null,
            "open": true,
            "userCount": 1
        },
        {
            "serverId": 9,
            "managerId": 1,
            "profile": "image.png",
            "description": null,
            "open": true,
            "userCount": 1
        },
        {
            "serverId": 8,
            "managerId": 1,
            "profile": "image.png",
            "description": null,
            "open": true,
            "userCount": 1
        },
        {
            "serverId": 7,
            "managerId": 1,
            "profile": "image.png",
            "description": null,
            "open": true,
            "userCount": 1
        },
        {
            "serverId": 2,
            "managerId": 1,
            "profile": "image.png",
            "description": null,
            "open": true,
            "userCount": 1
        },
        {
            "serverId": 4,
            "managerId": 1,
            "profile": "image.png",
            "description": null,
            "open": true,
            "userCount": 1
        },
        {
            "serverId": 3,
            "managerId": 1,
            "profile": "image.png",
            "description": null,
            "open": true,
            "userCount": 1
        }
    ]
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
    "message": "Ok",
    "data": {
        "numberOfElements": 1,
        "totalPages": 1,
        "hasNext": false,
        "data": [
            {
                "serverId": 1,
                "serverName": "example",
                "userCount": 2
            }
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
    "message": "Ok",
    "data": {
        "dmId": 1,
        "name": "abc, abc",
        "profile": null
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
    "message": "Ok",
    "data": {
        "dm": {
            "dmId": 1,
            "name": "abc, abc, abc, abc",
            "profile": null
        },
        "dmUserInfos": [
            {
                "userId": 1,
                "name": "test123"
            }
        ],
        "userOnOff": {
            "connectionStates": {
                "1": "online",
                "2": "offline",
                "3": "online",
                "4": "offline"
            }
        },
        "messages": null
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
    "message": "Ok",
    "data": {
        "dmId": 1,
        "name": "abc, abc, abc, abc",
        "profile": null
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
    "message": "Ok",
    "data": {
        "dmId": 1,
        "name": "hello",
        "profile": null
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
```javascript
const formdata = new FormData();
formdata.append("requestDto", "{\"dmId\":1}");
formdata.append("profile", fileInput.files[0], "image.png");

const requestOptions = {
  method: "PATCH",
  body: formdata,
  redirect: "follow"
};
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": {
        "dmId": 1,
        "name": "hello",
        "profile": "http://image.png"
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
```json
{
    "dmId" : 1,
    "profile" : "http://image.png"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "Dm profile delete success!!"
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
    "message": "Ok",
    "data": "DmUser delete success!!"
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
    "userId" : 1,
    "serverId" : 1,
    "name" : "채팅채널5"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": {
        "categoryId": 3,
        "name": "채팅채널5"
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
    "userId" : 1,
    "serverId" : 1,
    "categoryId" : 3,
    "name" : "채팅채널12"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": {
        "categoryId": 3,
        "name": "채팅채널12"
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
```json
{
    "userId" : 1,
    "serverId" : 1,
    "categoryId" : 3
}
```


### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "Category delete success!"
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
    "userId" : 1,
    "serverId" : 1,
    "categoryId" : 1,
    "channelType" : "CHAT",
    "name" : "테스트 채널"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": {
        "channelId": 3,
        "categoryId": 1,
        "channelType": "CHAT",
        "name": "테스트 채널"
    }
}
```

## 채널 읽기

| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 채널 읽기 |
| **설명** | 포럼 목록을 불러옵니다. |
| **메소드** | `GET` |
| **REST API** | `/api/community/channel/{channelId}/{userId}?title=3&page=1` |

### Request
(해당없음)


### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": {
        "content": [
            {
                "forumId": 10,
                "channelId": 3,
                "title": "테스트 포럼3",
                "writer": "abc",
                "content": "테스트 포럼3",
                "forumCategory": "CHALLENGE66",
                "createAt": "2024-05-11T19:45:33.102408",
                "updateAt": "2024-05-11T19:45:33.102408",
                "files": [
                    {
                        "fileId": 21,
                        "fileUrl": "http://image.png"
                    },
                    {
                        "fileId": 22,
                        "fileUrl": "http://image.png"
                    }
                ],
                "forumMessageCount" : 1
            },
            {
                "forumId": 8,
                "channelId": 3,
                "title": "테스트 포럼3",
                "writer": "abc",
                "content": "테스트 포럼3",
                "forumCategory": "CHALLENGE66",
                "createAt": "2024-05-09T13:19:51.189937",
                "updateAt": "2024-05-09T13:19:51.189937",
                "files": [
                    {
                        "fileId": 15,
                        "fileUrl": "http://image.png"
                    },
                    {
                        "fileId": 16,
                        "fileUrl": "http://image.png"
                    }
                ],
                "forumMessageCount" : 1
            },
            {
                "forumId": 7,
                "channelId": 3,
                "title": "테스트 포럼3",
                "writer": "abc",
                "content": "테스트 포럼3",
                "forumCategory": "CHALLENGE66",
                "createAt": "2024-05-09T13:19:50.629682",
                "updateAt": "2024-05-09T13:19:50.629682",
                "files": [
                    {
                        "fileId": 13,
                        "fileUrl": "http://image.png"
                    },
                    {
                        "fileId": 14,
                        "fileUrl": "http://image.png"
                    }
                ],
                "forumMessageCount" : 1
            },
            {
                "forumId": 6,
                "channelId": 3,
                "title": "테스트 포럼3",
                "writer": "abc",
                "content": "테스트 포럼3",
                "forumCategory": "CHALLENGE66",
                "createAt": "2024-05-09T13:19:50.1436",
                "updateAt": "2024-05-09T13:19:50.1436",
                "files": [
                    {
                        "fileId": 11,
                        "fileUrl": "http://image.png"
                    },
                    {
                        "fileId": 12,
                        "fileUrl": "http://image.png"
                    }
                ],
                "forumMessageCount" : 1
            },
            {
                "forumId": 5,
                "channelId": 3,
                "title": "테스트 포럼3",
                "writer": "abc",
                "content": "테스트 포럼3",
	              "forumCategory": "CHALLENGE66",
                "createAt": "2024-05-09T13:19:49.710342",
                "updateAt": "2024-05-09T13:19:49.710342",
                "files": [
                    {
                        "fileId": 9,
                        "fileUrl": "http://image.png"
                    },
                    {
                        "fileId": 10,
                        "fileUrl": "http://image.png"
                    }
                ],
                "forumMessageCount" : 1
            },
            {
                "forumId": 4,
                "channelId": 3,
                "title": "테스트 포럼3",
                "writer": "abc",
                "content": "테스트 포럼3",
                "forumCategory": "CHALLENGE66",
                "createAt": "2024-05-09T13:19:49.223886",
                "updateAt": "2024-05-09T13:19:49.223886",
                "files": [
                    {
                        "fileId": 7,
                        "fileUrl": "http://image.png"
                    },
                    {
                        "fileId": 8,
                        "fileUrl": "http://image.png"
                    }
                ],
                "forumMessageCount" : 1
            },
            {
                "forumId": 3,
                "channelId": 3,
                "title": "테스트 포럼3",
                "writer": "abc",
                "content": "테스트 포럼3",
                "forumCategory": "CHALLENGE66",
                "createAt": "2024-05-09T13:19:48.758317",
                "updateAt": "2024-05-09T13:19:48.758317",
                "files": [
                    {
                        "fileId": 5,
                        "fileUrl": "http://image.png"
                    },
                    {
                        "fileId": 6,
                        "fileUrl": "http://image.png"
                    }
                ],
                "forumMessageCount" : 1
            },
            {
                "forumId": 2,
                "channelId": 3,
                "title": "테스트 포럼3",
                "writer": "abc",
                "content": "테스트 포럼3",
                "forumCategory": "CHALLENGE66",
                "createAt": "2024-05-09T13:19:48.156112",
                "updateAt": "2024-05-09T13:19:48.156112",
                "files": [
                    {
                        "fileId": 3,
                        "fileUrl": "http://image.png"
                    },
                    {
                        "fileId": 4,
                        "fileUrl": "http://image.png"
                    }
                ],
                "forumMessageCount" : 1
            }
        ],
        "sort": {
            "sorted": true,
            "direction": "DESC",
            "orderProperty": "createdAt"
        },
        "currentPage": 0,
        "size": 15,
        "first": true,
        "last": true
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
    "userId" : 1,
    "serverId" : 1,
    "channelId" : 3,
    "categoryId" : 1,
    "name" : "테스트 업데이트 채널"
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": {
        "channelId": 3,
        "categoryId": 1,
        "channelType": "CHAT",
        "name": "테스트 업데이트 채널"
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
```json
{
    "userId" : 1,
    "serverId" : 1,
    "channelId" : 3
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "Channel delete success!"
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
formdata.append("requestDto", "{\"userId\":1, \"serverId\":1, \"channelId\":3, \"title\":\"테스트 포럼3\", \"content\":\"테스트 포럼3\", \"forumCategory\":\"CHALLENGE66\"}");
formdata.append("files", fileInput.files[0], "도서 검색1-1.png");
formdata.append("files", fileInput.files[0], "도서 검색2-2.png");
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": {
        "forumId": 1,
        "channelId": 3,
        "title": "테스트 포럼3",
        "writer": "test123",
        "content": "테스트 포럼3",
        "forumCategory": "CHALLENGE66",
        "createAt": "2024-06-01T12:55:23.275569",
        "updateAt": "2024-06-01T12:55:23.275569",
        "files": [
            {
                "fileId": 1,
                "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/community/b1ad4645-18fc-472c-ad8a-e8bea4de557a.png"
            },
            {
                "fileId": 2,
                "fileUrl": "https://fittrip-bucket.s3.ap-northeast-2.amazonaws.com/community/a379461b-03c2-4dfa-a978-b3eaf650de65.png"
            }
        ]
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
    "message": "Ok",
    "data": {
        "forum": {
            "forumId": 10,
            "channelId": 3,
            "title": "테스트 포럼3",
            "writer": "abc",
            "content": "테스트 포럼3",
            "forumCategory": "CHALLENGE66",
            "createAt": "2024-05-11T19:45:33.102408",
            "updateAt": "2024-05-11T19:45:33.102408",
            "files": [
                {
                    "fileId": 21,
                    "fileUrl": "http://image.png"
                },
                {
                    "fileId": 22,
                    "fileUrl": "http://image.png"
                }
            ]
        },
        "messages": null
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
formdata.append("requestDto", "{\"userId\":1, \"serverId\":1, \"channelId\":3, \"forumId\":2, \"title\":\"테스트 포럼2\", \"content\":\"테스트 포럼2\"}");
formdata.append("filesId", "[1]");
formdata.append("files", fileInput.files[0], "도서 검색3-1.png");

const requestOptions = {
  method: "PATCH",
  body: formdata,
  redirect: "follow"
};

fetch("http://localhost:8080/forum", requestOptions)
  .then((response) => response.text())
  .then((result) => console.log(result))
  .catch((error) => console.error(error));
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": {
        "title": "테스트 포럼2",
        "content": "테스트 포럼2",
        "createAt": "2024-05-09T13:19:42.910085",
        "updateAt": "2024-05-09T13:19:42.910085",
        "files": [
            {
                "fileId": 19,
                "fileUrl": "http://image.png"
            },
            {
                "fileId": 20,
                "fileUrl": "http://image.png"
            }
        ]
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
    "serverId" : 1,
    "channelId":3,
    "forumId":9,
    "userId": 1
}
```

### Response
```json
{
    "success": true,
    "code": 0,
    "message": "Ok",
    "data": "Forum delete success!!"
}
```




