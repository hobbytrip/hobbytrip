const BASE_URL = "https://fittrip.site/api";
const COMMUNITY_BASE_URL = "https://fittrip.site/api/community";
const MEDIA_BASE_URL = "https://fittrip.site/api/sig";
const NOTIFICATION_URL = "https://fittrip.site/api/notification";
const WS_BASE = "/ws/chat";
const API = {
  //유저
  LOG_IN: `${BASE_URL}/user/login`,
  SIGN_UP: `${BASE_URL}/user/signup`,
  LOG_OUT: `${BASE_URL}/user/logout`,
  GET_USER_PROFLIE: `${BASE_URL}/user/profile`,

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
  SERVER_SSE_SUB: `${BASE_URL}/user/api/server/subscribe`,
  DM_SSE_SUB: `${BASE_URL}/user/api/dm/subscribe`,

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
};

export default API;
