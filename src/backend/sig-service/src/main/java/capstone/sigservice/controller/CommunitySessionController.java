package capstone.sigservice.controller;

import capstone.sigservice.dto.ConnectionDto;
import capstone.sigservice.dto.UserLocationEventDto;
import capstone.sigservice.dto.VoiceChannelEventDto;
import capstone.sigservice.service.CommunitySessionService;
import com.google.gson.JsonObject;
import java.net.URISyntaxException;
import java.util.Map;


import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.java.client.Connection;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;


//@CrossOrigin(origins = "*")
@RestController
public class CommunitySessionController {



    @Autowired
    private CommunitySessionService coummintySessionService;

    @Autowired
    private KafkaTemplate<String, VoiceChannelEventDto> voiceConnectionStatekafkaTemplate;
    @Autowired
    private KafkaTemplate<String, UserLocationEventDto> userLocationEventKafkaTemplate;


    @Value("${spring.kafka.topic.voice-connection-state-event}")
    private String voiceConnectionStateTopic;
    @Value("${spring.kafka.topic.user-location-event}")
    private String userLocationEventTopic;

    //Service활용
    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {

        Session session = coummintySessionService.initializeSession(params);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }
//    @PostMapping("/api/sessions/{sessionId}/connections")
//    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
//                                                   @RequestBody(required = false) Map<String, Object> params)
//            throws OpenViduJavaClientException, OpenViduHttpException {
//        Session session = coummintySessionService.findSession(sessionId);
//        if (session == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        Connection connection = coummintySessionService.createConnection(sessionId,params);
//
//        VoiceChannelEventDto voiceDto=coummintySessionService.createJoinVoiceDto(params);
//        UserLocationEventDto locationDto=coummintySessionService.createUserLocationEventDto(params);
//        voiceConnectionStatekafkaTemplate.send(voiceConnectionStateTopic,voiceDto);
//        userLocationEventKafkaTemplate.send(userLocationEventTopic,locationDto);
//
//        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
//    }
    @PostMapping("/api/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false)ConnectionDto connectionDto)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = coummintySessionService.findSession(sessionId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String,Object> params=connectionDto.toMap();

        Connection connection = coummintySessionService.createConnection(sessionId,params);

        VoiceChannelEventDto voiceDto=coummintySessionService.createJoinVoiceDto(params);
        UserLocationEventDto locationDto=coummintySessionService.createUserLocationEventDto(params);
        voiceConnectionStatekafkaTemplate.send(voiceConnectionStateTopic,voiceDto);
        userLocationEventKafkaTemplate.send(userLocationEventTopic,locationDto);

        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

    @DeleteMapping("/api/sessions/{sessionId}/disconnect")
    public ResponseEntity<String> leaveConnection(@PathVariable("sessionId")String sessionId,
                                @RequestBody Map<String,Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException{
        VoiceChannelEventDto voiceDto=coummintySessionService.createLeaveVoiceDto(params);
        voiceConnectionStatekafkaTemplate.send(voiceConnectionStateTopic,voiceDto);
        return new ResponseEntity<>("퇴장",HttpStatus.OK);

    }

    @PostMapping (value = "/api/fit/sessions/getToken")
    public ResponseEntity<JsonObject> getToken(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        return coummintySessionService.generateToken(params);
    }
    @PostMapping("/api/fit/removeUser")
    public ResponseEntity<JsonObject> removeUser(@RequestBody Map<String, Object> sessionNameToken) {
        return coummintySessionService.removeUserFromSession(sessionNameToken);
    }
    @GetMapping("/api/fit/createRoom")
    public ResponseEntity<Object> getRoom(@RequestParam Map<String, String> sessionName, @RequestParam Map<String, String> UserName) throws URISyntaxException {
        return coummintySessionService.createRoom(sessionName, UserName);
    }
    @DeleteMapping("api/fit/closeSession")
    public ResponseEntity<JsonObject> deleteSession(@RequestBody Map<String, Object> sessionName) throws Exception {
        return coummintySessionService.deleteSession(sessionName);
    }


}