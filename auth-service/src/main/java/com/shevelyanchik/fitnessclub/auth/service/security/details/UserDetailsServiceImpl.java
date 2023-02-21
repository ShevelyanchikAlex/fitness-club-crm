package com.shevelyanchik.fitnessclub.auth.service.security.details;

import com.shevelyanchik.fitnessclub.auth.client.UserServiceClient;
import com.shevelyanchik.fitnessclub.auth.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserDto userDto = userServiceClient.findUserByEmail(email);
        return SecurityUser.fromUserDto(userDto);
    }
}
