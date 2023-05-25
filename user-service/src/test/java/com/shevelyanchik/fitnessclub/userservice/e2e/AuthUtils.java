package com.shevelyanchik.fitnessclub.userservice.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.userservice.e2e.dto.AuthenticationRequest;
import com.shevelyanchik.fitnessclub.userservice.e2e.dto.AuthenticationResponse;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class AuthUtils {

    private static final AuthenticationRequest EXPECTED_AUTH_REQUEST = new AuthenticationRequest(
            "filin@gmail.com", "userUseruser01");
    private static final String AUTH_API_ENDPOINT = "/auth-service/auth";

    private final ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    String login(TestRestTemplate testRestTemplate) {
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                AUTH_API_ENDPOINT + "/login", EXPECTED_AUTH_REQUEST, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        AuthenticationResponse authenticationResponse = objectMapper.readValue(
                response.getBody(), AuthenticationResponse.class);
        return authenticationResponse.getToken();
    }

    HttpHeaders configAuthHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return headers;
    }

}
