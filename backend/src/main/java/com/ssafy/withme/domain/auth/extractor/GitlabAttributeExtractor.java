package com.ssafy.withme.domain.auth;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

public class GitlabAttributeExtractor implements ProviderAttributeExtractor {
    @Override
    public String getUsername(OAuth2User oAuth2User) {
        return "";
    }

    @Override
    public String getUsername(Principal principal) {
        return "";
    }

    @Override
    public String getImageUrl(Principal principal) {
        return "";
    }
}
