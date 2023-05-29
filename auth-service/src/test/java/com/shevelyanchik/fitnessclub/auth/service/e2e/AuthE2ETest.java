package com.shevelyanchik.fitnessclub.auth.service.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationRequest;
import com.shevelyanchik.fitnessclub.auth.dto.AuthenticationResponse;
import com.shevelyanchik.fitnessclub.auth.dto.user.Role;
import com.shevelyanchik.fitnessclub.auth.dto.user.Status;
import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthE2ETest {

    private static final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Max", "Filin", "userUseruser01",
            "filin@gmail.com", "+375443321233", Role.USER, Status.ACTIVE);
    private static final AuthenticationRequest EXPECTED_AUTH_REQUEST = new AuthenticationRequest(
            "filin@gmail.com", "userUseruser01");
    private static final String BASE_API_URL = "http://localhost:8765/api/v1";
    private static final String AUTH_API_ENDPOINT = "/auth-service/auth";

    private TestRestTemplate testRestTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setupRestTemplate() {
        testRestTemplate = new TestRestTemplate(
                new RestTemplateBuilder()
                        .rootUri(BASE_API_URL)
        );
    }

    @Test
    @Order(1)
    @SneakyThrows
    void testSignUp() {
        //when
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                AUTH_API_ENDPOINT + "/signup", EXPECTED_USER_DTO, String.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        UserDto userDtoResponse = objectMapper.readValue(response.getBody(), UserDto.class);
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), userDtoResponse.getEmail());
        Assertions.assertEquals(EXPECTED_USER_DTO.getName(), userDtoResponse.getName());
        Assertions.assertEquals(EXPECTED_USER_DTO.getSurname(), userDtoResponse.getSurname());
    }

    @Test
    @Order(2)
    void testLogin() {
        //when
        ResponseEntity<AuthenticationResponse> response = testRestTemplate.postForEntity(
                AUTH_API_ENDPOINT + "/login", EXPECTED_AUTH_REQUEST, AuthenticationResponse.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        String userEmail = response.getBody().getEmail();
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), userEmail);
    }

}
