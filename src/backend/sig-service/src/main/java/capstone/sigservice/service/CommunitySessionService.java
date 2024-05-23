package capstone.sigservice.service;

import capstone.sigservice.dto.VoiceConnectionState;
import capstone.sigservice.dto.VoiceDto;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
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
    public VoiceDto createJoinVoiceDto(Map<String, Object> params){
        VoiceDto voiceDto=new VoiceDto();
        voiceDto.setServerId(Long.parseLong(String.valueOf(params.get("serverId"))));
        voiceDto.setChannelId(Long.parseLong(String.valueOf(params.get("channelId"))));
        voiceDto.setUserId(Long.parseLong(String.valueOf(params.get("userId"))));
        voiceDto.setVoiceConnectionState(VoiceConnectionState.VOICE_JOIN);
        return voiceDto;
    }
    public VoiceDto createLeaveVoiceDto(Map<String, Object> params){
        VoiceDto voiceDto=new VoiceDto();
        voiceDto.setServerId(Long.parseLong(String.valueOf(params.get("serverId"))));
        voiceDto.setChannelId(Long.parseLong(String.valueOf(params.get("channelId"))));
        voiceDto.setUserId(Long.parseLong(String.valueOf(params.get("userId"))));
        voiceDto.setVoiceConnectionState(VoiceConnectionState.VOICE_LEAVE);
        return voiceDto;
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

}

