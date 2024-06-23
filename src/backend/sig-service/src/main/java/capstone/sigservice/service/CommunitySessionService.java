package capstone.sigservice.service;

import capstone.sigservice.dto.UserLocationEventDto;
import capstone.sigservice.dto.VoiceConnectionState;
import capstone.sigservice.dto.VoiceChannelEventDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommunitySessionService {
    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    private Map<String, Map<String, OpenViduRole>> mapSessionNames = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    public Session initializeSession(Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException {
        //세션 설정
        SessionProperties properties = SessionProperties.fromJson(params).build();
        //세션 생성

        return openvidu.createSession(properties);
    }



    public Connection createConnection(String sessionId,Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {

        Session session=openvidu.getActiveSession(sessionId);
        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return connection;
    }
    public Session findSession(String sessionId){
        Session session=openvidu.getActiveSession(sessionId);
        if(session==null){
            return null;
        }
        return session;
    }
    public VoiceChannelEventDto createJoinVoiceDto(Map<String, Object> params){
        VoiceChannelEventDto voiceDto=new VoiceChannelEventDto();
        voiceDto.setServerId(Long.parseLong(String.valueOf(params.get("serverId"))));
        voiceDto.setChannelId(Long.parseLong(String.valueOf(params.get("channelId"))));
        voiceDto.setUserId(Long.parseLong(String.valueOf(params.get("userId"))));
        voiceDto.setVoiceConnectionState(VoiceConnectionState.VOICE_JOIN);
        return voiceDto;
    }
    public VoiceChannelEventDto createLeaveVoiceDto(Map<String, Object> params){
        VoiceChannelEventDto voiceDto=new VoiceChannelEventDto();
        voiceDto.setServerId(Long.parseLong(String.valueOf(params.get("serverId"))));
        voiceDto.setChannelId(Long.parseLong(String.valueOf(params.get("channelId"))));
        voiceDto.setUserId(Long.parseLong(String.valueOf(params.get("userId"))));
        voiceDto.setVoiceConnectionState(VoiceConnectionState.VOICE_LEAVE);
        return voiceDto;
    }
    public UserLocationEventDto createUserLocationEventDto(Map<String, Object> params){
        UserLocationEventDto locationDto=new UserLocationEventDto();
        locationDto.setServerId(Long.parseLong(String.valueOf(params.get("serverId"))));
        locationDto.setChannelId(Long.parseLong(String.valueOf(params.get("channelId"))));
        locationDto.setUserId(Long.parseLong(String.valueOf(params.get("userId"))));
        return locationDto;
    }
    public ResponseEntity<JsonObject> generateToken(Map<String, Object> params) {
        String sessionName = (String) params.get("sessionName");

        OpenViduRole role = OpenViduRole.PUBLISHER;
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(role)
                .data("user_data")
                .build();

        JsonObject responseJson = new JsonObject();

        if (mapSessions.get(sessionName) != null) {
            // Session already exists
            try {
                String token = mapSessions.get(sessionName).createConnection(connectionProperties).getToken();
                mapSessionNames.get(sessionName).put(token, role);
                responseJson.addProperty("0", token);
                return new ResponseEntity<>(responseJson, HttpStatus.OK);
            } catch (OpenViduJavaClientException | OpenViduHttpException e) {
                return handleException(sessionName, e);
            }
        } else {
            // New session
            try {
                Session session = openvidu.createSession();
                String token = session.createConnection(connectionProperties).getToken();
                mapSessions.put(sessionName, session);
                mapSessionNames.put(sessionName, new ConcurrentHashMap<>());
                mapSessionNames.get(sessionName).put(token, role);
                responseJson.addProperty("0", token);
                return new ResponseEntity<>(responseJson, HttpStatus.OK);
            } catch (Exception e) {
                return getErrorResponse(e);
            }
        }
    }
    private ResponseEntity<JsonObject> handleException(String sessionName, Exception e) {
        // Exception handling logic
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("error", "Exception occurred while generating token");
        return new ResponseEntity<>(responseJson, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<JsonObject> getErrorResponse(Exception e) {
        // General error handling logic
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("error", "Exception occurred while creating session");
        return new ResponseEntity<>(responseJson, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public ResponseEntity<JsonObject> removeUserFromSession(Map<String, Object> sessionNameToken) {
        String sessionName = (String) sessionNameToken.get("sessionName");
        String token = (String) sessionNameToken.get("token");

        System.out.println("Removing user | {sessionName, token}=" + sessionNameToken);

        if (mapSessions.get(sessionName) != null && mapSessionNames.get(sessionName) != null) {
            if (mapSessionNames.get(sessionName).remove(token) != null) {
                if (mapSessionNames.get(sessionName).isEmpty()) {
                    mapSessions.remove(sessionName);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                System.out.println("Problems in the app server: the TOKEN wasn't valid");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            System.out.println("Problems in the app server: the SESSION does not exist");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Object> createRoom(Map<String, String> sessionName, Map<String, String> userName) throws URISyntaxException {
        String sName = sessionName.get("sessionName");
        String nName = userName.get("nickName");
        System.out.println("sessionName: " + sName + ", nickName: " + nName);


        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.addProperty("sessionName", sName);
        json.addProperty("nickName", nName);
        System.out.println(json);


        URI uri = new URI("https://localhost:8080/");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        httpHeaders.add("sessionInfo", sName + "," + nName);

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }
    public ResponseEntity<JsonObject> deleteSession(Map<String, Object> sessionName) throws Exception {
        String session = (String) sessionName.get("sessionName");

        if (mapSessions.get(session) != null && mapSessionNames.get(session) != null) {
            Session s = mapSessions.get(session);
            s.close();
            mapSessions.remove(session);
            mapSessionNames.remove(session);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            System.out.println("세션이 존재하지 않습니다.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

