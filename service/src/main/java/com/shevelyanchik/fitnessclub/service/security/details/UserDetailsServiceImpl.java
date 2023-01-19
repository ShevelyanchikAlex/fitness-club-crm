package com.shevelyanchik.fitnessclub.service.security.details;

import com.shevelyanchik.fitnessclub.model.domain.user.User;
import com.shevelyanchik.fitnessclub.persistence.UserRepository;
import com.shevelyanchik.fitnessclub.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ServiceException("user.not.exist"));
        return SecurityUser.fromUser(user);
    }
}
