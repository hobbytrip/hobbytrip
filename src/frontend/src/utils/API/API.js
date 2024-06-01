const CHAT_API_BASE_URL = "http://localhost:7070";
const COMMUNITY_BASE_URL = "http://localhost:8080";
const WS_SERVER_BASE = "ws/api/chat/server/message";
const WS_DM_BASE = "ws/api/chat/direct/message";
const WS_FORUM_BASE = "/ws/api/chat/forum/message";

const API = {
  //서버(커뮤니티) 채팅
  SUBSCRIBE_CHAT: (serverId) => `/topic/server/${serverId}`,
  SEND_CHAT: `${WS_SERVER_BASE}/send`,
  MODIFY_CHAT: `${WS_SERVER_BASE}/modify`,
  DELETE_CHAT: `${WS_SERVER_BASE}/delete`,
  IS_TYPING: `${WS_SERVER_BASE}/typing`,
  FILE_UPLOAD: `${CHAT_API_BASE_URL}/server/message/file`,
  GET_HISTORY: `${CHAT_API_BASE_URL}/server/messages/channel`,
  GET_COMMENTS: `${CHAT_API_BASE_URL}/server/comments/message`,
  POST_LOCATION: `${CHAT_API_BASE_URL}/server/user/location`,

  //서버 이벤트
  SERVER: `${COMMUNITY_BASE_URL}/server`,
  GET_SERVER: (serverId, userId) =>`${COMMUNITY_BASE_URL}/server/${serverId}/${userId}`,
  INVITE_SERVER: (serverId) => `${COMMUNITY_BASE_URL}/server/${serverId}/invitation`,
  JOIN_SERVER: `${COMMUNITY_BASE_URL}/server/join`,
  COMM_CATEGORY:`${COMMUNITY_BASE_URL}/category`,
  COMM_CHANNEL: `${COMMUNITY_BASE_URL}/channel`,
  

  //DM 채팅
  SUBSCRIBE_DM: (roomId) => `/topic/direct/${roomId}`,
  SEND_DM: `${WS_DM_BASE}/send`,
  MODIFY_DM: `${WS_DM_BASE}/modify`,
  DELETE_DM: `${WS_DM_BASE}/delete`,
  IS_TYPING_DM: `${WS_DM_BASE}/typing`,
  DM_FILE_UPLOAD: `${CHAT_API_BASE_URL}/direct/message/file`,
  GET_DM_HISTORY: `${CHAT_API_BASE_URL}/direct/messages/channel`,
  GET_DM_COMMENTS: `${CHAT_API_BASE_URL}/direct/comments`,

  //DM이벤트

  //FORUM 채팅
  SEND_FORUM: `${WS_FORUM_BASE}/send`,
  MODIFY_FORUM: `${WS_FORUM_BASE}/modify`,
  DELETE_FORUM: `${WS_FORUM_BASE}/delete`,
  IS_TYPING_FORUM: `${WS_FORUM_BASE}/typing`,
  FORUM_FILE_UPLOAD: `${CHAT_API_BASE_URL}/forum/message/file`,
  GET_FORUM_HISTORY: `${CHAT_API_BASE_URL}/forum/messages/forum`,
  GET_FORUM_COMMENTS: `${CHAT_API_BASE_URL}/forum/comments`,

  //FORUM 이벤트
};

export default API;
