package com.shevelyanchik.fitnessclub.orderservice.client;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.TrainerDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/v1/users/{id}")
    UserDto findUserById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/trainers/{id}")
    TrainerDto findTrainerById(@PathVariable("id") Long id);
}
