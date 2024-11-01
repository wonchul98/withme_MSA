package com.ssafy.withme.domain.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String tokenValue = userRequest.getAccessToken().getTokenValue();
        log.info("tokenValue: {}", tokenValue);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        //TODO : 유저를 찾고 없으면 DB에 추가한다
        //Github access token을 저장해야 하는지 확인한다
        return new CustomOAuth2User(oAuth2User, tokenValue);
    }
}
