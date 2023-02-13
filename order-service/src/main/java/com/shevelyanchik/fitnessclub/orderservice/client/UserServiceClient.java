package com.shevelyanchik.fitnessclub.orderservice.client;

import com.shevelyanchik.fitnessclub.orderservice.model.dto.TrainerDto;
import com.shevelyanchik.fitnessclub.orderservice.model.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/{id}")
    UserDto findUserById(@PathVariable Long id);

    @GetMapping("/{id}")
    TrainerDto findTrainerById(@PathVariable Long id);
}
