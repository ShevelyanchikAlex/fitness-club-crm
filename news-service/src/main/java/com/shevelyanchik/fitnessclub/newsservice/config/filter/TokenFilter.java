package com.shevelyanchik.fitnessclub.newsservice.config.filter;

import com.shevelyanchik.fitnessclub.newsservice.constant.Permission;
import com.shevelyanchik.fitnessclub.newsservice.constant.Role;
import com.shevelyanchik.fitnessclub.newsservice.model.dto.UserAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenFilter extends GenericFilterBean {

    private static final String HEADER_USERNAME = "username";
    private static final String HEADER_AUTHORITIES = "authorities";
    private static final String DEFAULT_CREDENTIALS = "secret";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        UserAuthentication userAuthentication = getUserAuthentication((HttpServletRequest) request);

        try {
            Authentication authentication;
            log.info("Username: {}", userAuthentication.getUsername());
            log.info("Authentication: {}", userAuthentication.getAuthorities());
            if (Objects.nonNull(userAuthentication.getUsername()) && Objects.nonNull(userAuthentication.getAuthorities())) {
                List<SimpleGrantedAuthority> simpleGrantedAuthorities = Arrays.stream(userAuthentication.getAuthorities().split(","))
                        .filter(Permission::existPermissionByName)
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                authentication = new UsernamePasswordAuthenticationToken(
                        userAuthentication.getUsername(), DEFAULT_CREDENTIALS, simpleGrantedAuthorities);
            } else {
                authentication = new UsernamePasswordAuthenticationToken(
                        Role.GUEST.name(), DEFAULT_CREDENTIALS, List.of(new SimpleGrantedAuthority(Permission.GUEST_PERMISSION.name())));
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

    private UserAuthentication getUserAuthentication(HttpServletRequest request) {
        return new UserAuthentication(request.getHeader(HEADER_USERNAME), request.getHeader(HEADER_AUTHORITIES));
    }

}
