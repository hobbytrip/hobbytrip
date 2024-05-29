//package com.capstone.notificationservice.controller;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static reactor.core.publisher.Mono.when;
//
//import com.capstone.notificationservice.domain.dm.service.DmNotificationService;
//import org.junit.jupiter.api.Test;
//import org.reactivestreams.Publisher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class DmDmNotificationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private DmNotificationService notificationService;
//
//    @Test
//    public void testSubscribe() throws Exception {
//        Long userId = 1L;
//        String lastEventId = "";
//
//        when((Publisher<?>) notificationService.subscribe(anyLong(), anyString())).thenReturn(new SseEmitter());
//
//        mockMvc.perform(get("/subscribe")
//                        .param("userId", userId.toString())
//                        .header("Last-Event-ID", lastEventId)
//                        .accept(MediaType.TEXT_EVENT_STREAM))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM));
//    }
//}
