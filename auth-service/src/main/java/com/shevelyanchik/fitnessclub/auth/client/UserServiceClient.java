package com.shevelyanchik.fitnessclub.auth.client;

import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/api/v1/user-service/users")
public interface UserServiceClient {
    @PostMapping("/create")
    UserDto createUser(@RequestBody UserDto userDto);

    @GetMapping("/email/{email}")
    UserDto findUserByEmail(@PathVariable("email") String email);

    @GetMapping("/exists-by-email/{email}")
    boolean existsUserByEmail(@PathVariable("email") String email);
}
