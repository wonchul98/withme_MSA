package com.ssafy.withme.global.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) // 인증된 요청만 허용
                .addFilterBefore(new CustomAuthFilter(), UsernamePasswordAuthenticationFilter.class); // 사용자 정의 필터 추가

        return http.build();
    }

    // 커스텀 필터 (헤더에 유저 정보가 있는지 확인)
    public static class CustomAuthFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String userId = request.getHeader("X-User-Id"); // 헤더에서 유저 정보 가져오기

            if (userId == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User ID missing in header");
                return;
            }

            filterChain.doFilter(request, response); // 다음 필터로 넘김
        }
    }
}
