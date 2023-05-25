package com.shevelyanchik.fitnessclub.userservice.unit.persistence;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.entity.Trainer;
import com.shevelyanchik.fitnessclub.userservice.model.entity.User;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.TrainerMapper;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.TrainerMapperImpl;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapper;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapperImpl;
import com.shevelyanchik.fitnessclub.userservice.persistence.TrainerRepository;
import com.shevelyanchik.fitnessclub.userservice.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
class TrainerRepositoryTest {

    private static final UserDto EXPECTED_USER_DTO = new UserDto(
            1L, "Name", "Surname", "passUser1",
            "test@gmail.com", "+375443321233", "", Role.USER, Status.ACTIVE);

    private static final TrainerDto EXPECTED_TRAINER_DTO = new TrainerDto(
            1L, "Higher", "Box", EXPECTED_USER_DTO);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Spy
    @InjectMocks
    private final TrainerMapper trainerMapper = new TrainerMapperImpl();

    @Spy
    private final UserMapper userMapper = new UserMapperImpl();


    @AfterEach
    void deleteUsers() {
        userRepository.deleteAll();
    }


    @Test
    void testCreateTrainer() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        Trainer expectedTrainer = trainerMapper.toEntity(EXPECTED_TRAINER_DTO);
        //when
        Trainer actualTrainer = saveTrainer(expectedUser, expectedTrainer);
        //then
        Assertions.assertEquals(expectedTrainer.getCategory(), actualTrainer.getCategory());
        Assertions.assertEquals(expectedTrainer.getUser(), actualTrainer.getUser());
    }

    @Test
    void testFindTrainerById() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        Trainer expectedTrainer = trainerMapper.toEntity(EXPECTED_TRAINER_DTO);
        //when
        Trainer savedTrainer = saveTrainer(expectedUser, expectedTrainer);
        long savedTrainerId = savedTrainer.getId();
        Trainer actualTrainer = trainerRepository.findById(savedTrainerId).orElse(null);
        //then
        Assertions.assertNotNull(actualTrainer);
    }


    @Test
    void testFindAllTrainers() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        Trainer expectedTrainer = trainerMapper.toEntity(EXPECTED_TRAINER_DTO);
        long expectedTrainersCount = 1L;
        //when
        saveTrainer(expectedUser, expectedTrainer);
        List<Trainer> actualTrainers = trainerRepository.findAll();
        //then
        Assertions.assertEquals(expectedTrainersCount, actualTrainers.size());
    }

    @Test
    void testCountTrainers() {
        //given
        User expectedUser = userMapper.toEntity(EXPECTED_USER_DTO);
        Trainer expectedTrainer = trainerMapper.toEntity(EXPECTED_TRAINER_DTO);
        long expectedTrainersCount = 1L;
        //when
        saveTrainer(expectedUser, expectedTrainer);
        long actualTrainersCount = trainerRepository.count();
        //then
        Assertions.assertEquals(expectedTrainersCount, actualTrainersCount);
    }

    private Trainer saveTrainer(User expectedUser, Trainer expectedTrainer) {
        User savedUser = userRepository.save(expectedUser);
        expectedTrainer.setUser(savedUser);
        return trainerRepository.save(expectedTrainer);
    }

}
