package com.shevelyanchik.fitnessclub.userservice.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrainerE2ETest {

    private static final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Max", "Filin", "userUseruser01",
            "test@gmail.com", "+375443321233", "", Role.USER, Status.ACTIVE);
    private static final String BASE_API_URL = "http://localhost:8765/api/v1";
    private static final String TRAINER_API_ENDPOINT = "/user-service/trainers";
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
    void testCreateTrainer() {
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);

        UserDto savedUserDto = createUser();
        TrainerDto expectedTrainerDto = new TrainerDto(1L, "Higher", "Box", savedUserDto);

        HttpEntity<TrainerDto> request = new HttpEntity<>(expectedTrainerDto, headers);
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                TRAINER_API_ENDPOINT + "/create", request, String.class);
        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        TrainerDto trainerDtoResponse = objectMapper.readValue(response.getBody(), TrainerDto.class);
        Assertions.assertEquals(expectedTrainerDto.getCategory(), trainerDtoResponse.getCategory());
        Assertions.assertEquals(expectedTrainerDto.getKindOfSport(), trainerDtoResponse.getKindOfSport());
    }

    @Test
    @Order(2)
    void testCountTrainers() {
        //when
        String token = AuthUtils.login(testRestTemplate);
        HttpHeaders headers = AuthUtils.configAuthHeader(token);
        HttpEntity<String> requestHeaders = new HttpEntity<>(headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                TRAINER_API_ENDPOINT + "/count", HttpMethod.GET,
                requestHeaders, String.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @SneakyThrows
    private UserDto createUser() {
        //when
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                AUTH_API_ENDPOINT + "/signup", EXPECTED_USER_DTO, String.class);
        //then
        Assertions.assertNotNull(response.getBody());
        return objectMapper.readValue(response.getBody(), UserDto.class);
    }

}
