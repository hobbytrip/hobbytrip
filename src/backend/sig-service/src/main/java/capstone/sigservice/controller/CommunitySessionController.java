package capstone.sigservice.controller;

import capstone.sigservice.dto.VoiceDto;
import capstone.sigservice.service.CommunitySessionService;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.java.client.Connection;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;


@CrossOrigin(origins = "*")
@RestController
public class CommunitySessionController {



    @Autowired
    private CommunitySessionService coummintySessionService;

    @Autowired
    private KafkaTemplate<String, VoiceDto> kafkaTemplate;


    //Service활용
    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {

        Session session = coummintySessionService.initializeSession(params);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }
    @PostMapping("/api/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = coummintySessionService.findSession(sessionId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Connection connection = coummintySessionService.createConnection(sessionId,params);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }




}