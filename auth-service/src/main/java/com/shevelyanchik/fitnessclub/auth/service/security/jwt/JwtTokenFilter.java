package com.shevelyanchik.fitnessclub.auth.service.security.jwt;

import com.shevelyanchik.fitnessclub.auth.attribute.AttributeName;
import com.shevelyanchik.fitnessclub.auth.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private static final String JWT_TOKEN_INVALID = "jwt.token.invalid";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        try {
            if (Objects.nonNull(token) && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (Objects.nonNull(authentication)) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    request.setAttribute(AttributeName.USERNAME, authentication.getPrincipal());
                    request.setAttribute(AttributeName.AUTHORITIES, authentication.getAuthorities());
                }
            }
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) response).sendError(e.hashCode());
            throw new AuthenticationException(JWT_TOKEN_INVALID);
        }
        chain.doFilter(request, response);
    }
}
