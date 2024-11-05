package com.ssafy.withme.domain.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.withme.global.util.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        TokenAndInfoResponse tokenResponse = new TokenAndInfoResponse();
        tokenResponse.setAccessToken(jwtUtils.issueToken(principal.getAccessToken(), principal.getMember().getId()));
        tokenResponse.setImageUrl((String) principal.getAttributes().get("avatar_url"));
        tokenResponse.setUserName((String) principal.getAttributes().get("login"));

        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), tokenResponse);
    }
}
