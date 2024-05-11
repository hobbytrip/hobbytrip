package com.capstone.userservice;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.capstone.userservice.domain.friend.dto.FriendStatusDto;
import com.capstone.userservice.domain.friend.dto.response.FriendsStatusResponse;
import com.capstone.userservice.domain.friend.service.FriendsStatusClient;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;


@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "api.authentication-server.url=http://localhost:${wiremock.server.port}",
        "spring.thymeleaf.enabled=false"})
public class FriendReadTest {

    @Autowired
    private FriendsStatusClient friendsStatusClient;

    @Autowired
    private WireMockServer wireMockServer;

    @Test
    public void 친구리스트_올바르게_들어와야한다() throws IOException {
        Path file = ResourceUtils.getFile("payload/settlement-response.json").toPath();

        stubFor(get(urlPathEqualTo("/friends/1")).willReturn(aResponse().withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).withBody(Files.readAllBytes(file))));

        FriendsStatusResponse response = friendsStatusClient.getFriendStatus(1L);

        // friendId가 6인 친구의 isOffline 상태를 검증
        FriendStatusDto firstFriendStatus = response.getFriendsStatus().stream().filter(f -> f.getFriendId().equals(7L))
                .findFirst().orElse(null);

        Assertions.assertThat(firstFriendStatus).isNotNull();
        Assertions.assertThat(firstFriendStatus.getIsOffline()).isTrue();
    }
}
