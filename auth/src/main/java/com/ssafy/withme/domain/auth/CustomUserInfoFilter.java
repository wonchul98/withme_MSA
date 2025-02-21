package com.ssafy.withme.domain.auth;

import com.ssafy.withme.global.exception.BusinessException;
import com.ssafy.withme.global.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomUserInfoFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);
        if (token == null) {
            throw new BusinessException(ErrorCode.INVALID_ID_TOKEN);
        }

        try {
            Jwt decodedToken = jwtDecoder.decode(token);
            String userId = decodedToken.getClaim("id");

            if (userId == null) {
                throw new BusinessException(ErrorCode.INVALID_ID_TOKEN);
            }

            response.setHeader("X-User-Id", userId);

        } catch (JwtException e) {
            throw new BusinessException(ErrorCode.INVALID_ID_TOKEN);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
    }
}

