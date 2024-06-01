const CHAT_API_BASE_URL = "http://localhost:7070";
// const COMMUNITY_BASE_URL = "https://fittrip.site/api/community";
const MEDIA_BASE_URL = "http://localhost:5000";
const TEST_API_BASE_URL = "https://fittrip.site/api";
const WS_SERVER_BASE = "ws/api/chat/server/message";
const WS_DM_BASE = "ws/api/chat/direct/message";
const WS_FORUM_BASE = "/ws/api/chat/forum/message";

const API = {
  //유저
  LOG_IN: `/user/login`,
  SIGN_UP: `/user/signup`,
  LOG_OUT: `/user/logout`,
  GET_USER_PROFLIE: `/user/profile`,

  //커뮤니티 회원가입
  COMM_SIGNUP: `${TEST_API_BASE_URL}/community/user`,
  READ_MAIN: (userId) => `${TEST_API_BASE_URL}/community/user/${userId}`,

  //서버(커뮤니티) 채팅
  SUBSCRIBE_CHAT: (serverId) => `/topic/server/${serverId}`,
  SEND_CHAT: `${WS_SERVER_BASE}/send`,
  MODIFY_CHAT: `${WS_SERVER_BASE}/modify`,
  DELETE_CHAT: `${WS_SERVER_BASE}/delete`,
  IS_TYPING: `${WS_SERVER_BASE}/typing`,
  FILE_UPLOAD: `${TEST_API_BASE_URL}/chat/server/message/file`,
  GET_HISTORY: `${TEST_API_BASE_URL}/chat/server/messages/channel`,
  GET_COMMENTS: `${TEST_API_BASE_URL}/chat/server/comments/message`,
  POST_LOCATION: `${TEST_API_BASE_URL}/chat/server/user/location`,

  //서버 이벤트
  COMM: `${TEST_API_BASE_URL}`,
  COMM_SERVER: `${TEST_API_BASE_URL}/community/server`,
  COMM_CATEGORY: `${TEST_API_BASE_URL}/community/category`,
  COMM_CHANNEL: `${TEST_API_BASE_URL}/community/channel`,

  //미디어
  MEDIA: `${MEDIA_BASE_URL}/api/sessions`,

  //DM 채팅
  SUBSCRIBE_DM: (roomId) => `/topic/direct/${roomId}`,
  SEND_DM: `${WS_DM_BASE}/send`,
  MODIFY_DM: `${WS_DM_BASE}/modify`,
  DELETE_DM: `${WS_DM_BASE}/delete`,
  IS_TYPING_DM: `${WS_DM_BASE}/typing`,
  DM_FILE_UPLOAD: `${CHAT_API_BASE_URL}/direct/message/file`,
  GET_DM_HISTORY: `${CHAT_API_BASE_URL}/direct/messages/channel`,
  GET_DM_COMMENTS: `${CHAT_API_BASE_URL}/direct/comments`,

  //FORUM 채팅
  SEND_FORUM: `${WS_FORUM_BASE}/send`,
  MODIFY_FORUM: `${WS_FORUM_BASE}/modify`,
  DELETE_FORUM: `${WS_FORUM_BASE}/delete`,
  IS_TYPING_FORUM: `${WS_FORUM_BASE}/typing`,
  FORUM_FILE_UPLOAD: `${TEST_API_BASE_URL}/chat/forum/message/file`,
  GET_FORUM_HISTORY: `${TEST_API_BASE_URL}/chat/forum/messages/forum`,
  GET_FORUM_COMMENTS: `${TEST_API_BASE_URL}/chat/forum/comments`,

  //FORUM 커뮤니티
  CREATE_FORUM: (forumId) => `${TEST_API_BASE_URL}/community/forum/${forumId}`,
  UD_FORUM: `${TEST_API_BASE_URL}/community/forum`,
};

export default API;
