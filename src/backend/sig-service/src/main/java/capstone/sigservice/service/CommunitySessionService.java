package capstone.sigservice.service;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
public class CommunitySessionService {
    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;
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

}

