const API_BASE_URL = "http://localhost:7070";
const WS_SERVER_BASE = "ws/api/chat/server/message";
const WS_DM_BASE = "ws/api/chat/direct/message";
const WS_FORUM_BASE = "/ws/api/chat/forum/message";

const API = {
  //서버
  SUBSCRIBE_CHAT: (serverId) => `/topic/server/${serverId}`,
  SEND_CHAT: `${WS_SERVER_BASE}/send`,
  MODIFY_CHAT: `${WS_SERVER_BASE}/modify`,
  DELETE_CHAT: `${WS_SERVER_BASE}/delete`,
  IS_TYPING: `${WS_SERVER_BASE}/typing`,
  FILE_UPLOAD: `${API_BASE_URL}/server/message/file`,
  GET_HISTORY: `${API_BASE_URL}/server/messages/channel`,
  GET_COMMENTS: `${API_BASE_URL}/server/comments/message`,
  POST_LOCATION: `${API_BASE_URL}/server/user/location`,

  //DM
  SUBSCRIBE_DM: (roomId) => `/topic/direct/${roomId}`,
  SEND_DM: `${WS_DM_BASE}/send`,
  MODIFY_DM: `${WS_DM_BASE}/modify`,
  DELETE_DM: `${WS_DM_BASE}/delete`,
  IS_TYPING_DM: `${WS_DM_BASE}/typing`,
  DM_FILE_UPLOAD: `${API_BASE_URL}/direct/message/file`,
  GET_DM_HISTORY: `${API_BASE_URL}/direct/messages/channel`,
  GET_DM_COMMENTS: `${API_BASE_URL}/direct/comments`,

  //FORUM
  SEND_FORUM: `${WS_FORUM_BASE}/send`,
  MODIFY_FORUM: `${WS_FORUM_BASE}/modify`,
  DELETE_FORUM: `${WS_FORUM_BASE}/delete`,
  IS_TYPING_FORUM: `${WS_FORUM_BASE}/typing`,
  FORUM_FILE_UPLOAD: `${API_BASE_URL}/forum/message/file`,
  GET_FORUM_HISTORY: `${API_BASE_URL}/forum/messages/forum`,
  GET_FORUM_COMMENTS: `${API_BASE_URL}/forum/comments`,
};

export default API;
