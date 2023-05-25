package com.shevelyanchik.fitnessclub.userservice.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserE2ETest {

    private static final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Max", "Filin", "userUseruser01",
            "filin@gmail.com", "+375443321233", "", Role.USER, Status.ACTIVE);
    private static final String BASE_API_URL = "http://localhost:8765/api/v1";
    private static final String USER_API_ENDPOINT = "/user-service/users";

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
    void testCreateUser() {
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<UserDto> request = new HttpEntity<>(EXPECTED_USER_DTO, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                USER_API_ENDPOINT + "/create", request, String.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        UserDto userDtoResponse = objectMapper.readValue(response.getBody(), UserDto.class);
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), userDtoResponse.getEmail());
        Assertions.assertEquals(EXPECTED_USER_DTO.getName(), userDtoResponse.getName());
        Assertions.assertEquals(EXPECTED_USER_DTO.getSurname(), userDtoResponse.getSurname());
    }

    @Test
    @Order(2)
    @SneakyThrows
    void testFindUserById() {
        //given
        long expectedId = EXPECTED_USER_DTO.getId();
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<Long> request = new HttpEntity<>(expectedId, headers);

        ResponseEntity<String> response = testRestTemplate.getForEntity(
                USER_API_ENDPOINT + "/{id}", String.class, request);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        UserDto userDtoResponse = objectMapper.readValue(response.getBody(), UserDto.class);
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), userDtoResponse.getEmail());
        Assertions.assertEquals(EXPECTED_USER_DTO.getName(), userDtoResponse.getName());
        Assertions.assertEquals(EXPECTED_USER_DTO.getSurname(), userDtoResponse.getSurname());
    }

    @Test
    @Order(3)
    @SneakyThrows
    void testFindUserByEmail() {
        //given
        String expectedEmail = EXPECTED_USER_DTO.getEmail();
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<String> request = new HttpEntity<>(expectedEmail, headers);

        ResponseEntity<String> response = testRestTemplate.getForEntity(
                USER_API_ENDPOINT + "/email/{email}", String.class, request);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        UserDto userDtoResponse = objectMapper.readValue(response.getBody(), UserDto.class);
        Assertions.assertEquals(EXPECTED_USER_DTO.getEmail(), userDtoResponse.getEmail());
        Assertions.assertEquals(EXPECTED_USER_DTO.getName(), userDtoResponse.getName());
        Assertions.assertEquals(EXPECTED_USER_DTO.getSurname(), userDtoResponse.getSurname());
    }

    @Test
    @Order(4)
    void testCountUsers() {
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                USER_API_ENDPOINT + "/count", String.class, request);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

}
