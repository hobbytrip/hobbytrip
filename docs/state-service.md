## 서버 유저 상태 정보 요청
| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 서버 유저 상태 정보 요청 |
| **설명** | 서버 음성 채널 유저 입,퇴장 상태와 유저 on/off 상태 정보를 반환 |
| **메소드** | `GET` |
| **REST API** | `/api/state/feign/server/user/state?serverId=1&userIds=1,2,3,4,5,6` |

    {
        "voiceChannelUsersState": {
            "1": [13, 22, 11],
            "2": [16, 25],
            "3": [29]
        },
        "usersConnectionState": {
            "1": "OFFLINE",
            "2": "OFFLINE",
            "5": "ONLINE",
            "6": "ONLINE"
        }
    }


## 유저 접속 상태 정보 요청
| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 유저 접속 상태 정보 요청 |
| **설명** | 유저 on/off 상태 정보를 반환 |
| **메소드** | `GET` |
| **REST API** | `/api/state/feign/user/connection/state?userIds=1,2,3,4,5,6,11,12` |

    {
        "usersConnectionState": {
            "1": "OFFLINE",
            "2": "OFFLINE",
            "5": "ONLINE",
            "6": "ONLINE",
            "11": "ONLINE",
            "12": "ONLINE"
        }
    }


## 유저 서버 위치 요청
| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 유저 서버 위치 요청 |
| **설명** | 사용자의 마지막 채널 위치값 반환 |
| **메소드** | `GET` |
| **REST API** | `/api/state/feign/1/1` |

    {
        "channelId": 3
    }

## 유저의 연결 상태 정보 업데이트
| 항목 | 내용 |
|---|---|
| **설계 완료** | Yes |
| **기능** | 유저의 연결 상태 정보 업데이트 |
| **설명** | 유저의 온라인, 오프라인 상태 정보 업데이트(웹 소켓 연결되면 온라인, 끊기면 오프라인) |
| **메소드** | `POST` |
| **REST API** | `/api/state/connection/info` |

    {
        "success": true,
        "code": 0,
        "message": "Ok",
        "data": {
            "userId": 1
        }
    }
