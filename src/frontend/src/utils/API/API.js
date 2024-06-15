const BASE_URL = "https://fittrip.site/api";
const COMMUNITY_BASE_URL = "https://fittrip.site/api/community";
const MEDIA_BASE_URL = "https://fittrip.site/api/sig";
const WS_BASE = "/ws/chat";
const NOTICE_URL = "https://fittrip.site/api/notice";
const FRIEND_URL = "https://fittrip.site/api/user/friends";
const STATE_URL = "https://fittrip.site/api/user/state";

const API = {
  //유저
  LOG_IN: `${BASE_URL}/user/login`,
  SIGN_UP: `${BASE_URL}/user/signup`,
  LOG_OUT: `${BASE_URL}/user/logout`,
  GET_USER_PROFLIE: `${BASE_URL}/user/profile`,

  //친구
  ADD_FRIEND: (email) => `${FRIEND_URL}/${email}`,
  WAITING_FRIEND: `${FRIEND_URL}/received`,
  ACCEPT_FRIEND_REQUEST: (friendShipId) =>
    `${FRIEND_URL}/approve/${friendShipId}`,
  REFUSE_FRIEND_REQUEST: (friendShipId) =>
    `${FRIEND_URL}/delete/${friendShipId}`,
  GET_FRIENDS: `${FRIEND_URL}/friendList`,

  //커뮤니티 회원가입
  COMM_SIGNUP: `${BASE_URL}/community/user`,
  READ_MAIN: (userId) => `${BASE_URL}/community/user/${userId}`,

  //const WS_BASE = "ws/chat";
  //소켓
  SEND_CHAT: (TYPE) => `${WS_BASE}/${TYPE}/message/send`,
  DELETE_CHAT: (TYPE) => `${WS_BASE}/${TYPE}/message/delete`,
  MODIFY_CHAT: (TYPE) => `${WS_BASE}/${TYPE}/message/modify`,
  ISTYPING: (TYPE) => `${WS_BASE}/${TYPE}/message/typing`,

  //서버(커뮤니티) 채팅
  SUBSCRIBE_CHAT: (serverId) => `/topic/server/${serverId}`,
  FILE_UPLOAD: `${BASE_URL}/chat/server/message/file`,
  GET_HISTORY: `${BASE_URL}/chat/server/messages/channel`,
  GET_COMMENTS: `${BASE_URL}/chat/server/comments/message`,
  POST_LOCATION: `${BASE_URL}/chat/server/user/location`,

  //서버 이벤트
  COMM_SERVER: `${COMMUNITY_BASE_URL}/server`,
  GET_SERVER: (serverId, userId) =>
    `${COMMUNITY_BASE_URL}/server/${serverId}/${userId}`,
  INVITE_SERVER: (serverId) =>
    `${COMMUNITY_BASE_URL}/server/${serverId}/invitation`,
  JOIN_SERVER: `${COMMUNITY_BASE_URL}/server/join`,
  COMM_CATEGORY: `${COMMUNITY_BASE_URL}/category`,
  COMM_CHANNEL: `${COMMUNITY_BASE_URL}/channel`,

  //미디어
  MEDIA: `${MEDIA_BASE_URL}/api/sessions`,

  // 알림
  SERVER_SSE_SUB: (userId) => `${NOTICE_URL}/server/subscribe?userId=${userId}`,
  DM_SSE_SUB: (userId) => `${NOTICE_URL}/dm/subscribe?userId=${userId}`,
  SERVER_SSE_MAIN: (userId) =>
    `${NOTICE_URL}/server/serverIds?userId=${userId}`,
  DM_SSE_MAIN: (userId) => `${NOTICE_URL}/dm/dmRoomIds?userId=${userId}`,
  SERVER_SSE_DEL: (serverId, userId) =>
    `${NOTICE_URL}/server/${serverId}?userId=${userId}`,
  DM_SSE_DEL: (roomId, userId) =>
    `${NOTICE_URL}/server/${roomId}?userId=${userId}`,

  //DM 채팅
  SUBSCRIBE_DM: (roomId) => `/topic/direct/${roomId}`,
  DM_FILE_UPLOAD: `${BASE_URL}/chat/direct/message/file`,
  GET_DM_HISTORY: `${BASE_URL}/chat/direct/messages/channel`,
  GET_DM_COMMENTS: `${BASE_URL}/chat/direct/comments`,

  //FORUM 채팅
  FORUM_FILE_UPLOAD: `${BASE_URL}/chat/forum/message/file`,
  GET_FORUM_HISTORY: `${BASE_URL}/chat/forum/messages/forum`,
  GET_FORUM_COMMENTS: `${BASE_URL}/chat/forum/comments`,

  //FORUM 이벤트
  READ_FORUM: (channelId, userId) =>
    `${BASE_URL}/community/channel/${channelId}/${userId}`,
  CUD_FORUM: `${BASE_URL}/community/forum`,

  //상태관리 
  GET_SERVER_STATE: (serverId, userIds) =>
    `${STATE_URL}?serverId=${serverId}&userIds=${userIds}`,
};

export default API;
