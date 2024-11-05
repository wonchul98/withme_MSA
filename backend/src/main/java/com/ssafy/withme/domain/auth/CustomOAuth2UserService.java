package com.ssafy.withme.domain.auth;

import com.ssafy.withme.domain.member.entity.Member;
import com.ssafy.withme.domain.member.entity.Provider;
import com.ssafy.withme.domain.member.repository.MemberRepository;
import com.ssafy.withme.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Provider provider = registrationId.equals(String.valueOf(Provider.GITHUB).toLowerCase()) ? Provider.GITHUB : Provider.GITLAB;
        String tokenValue = userRequest.getAccessToken().getTokenValue();

        OAuth2User oAuth2User = super.loadUser(userRequest);

        //github 우선
        String userName = oAuth2User.getAttribute("login");

        Optional<Member> result = memberRepository.findByUsernameAndProvider(userName, provider);
        Member member = result.orElseGet(() -> {
            Member newMember = new Member(userName, provider);

            newMember = memberRepository.save(newMember);
            return newMember;
        });

        return new CustomOAuth2User(oAuth2User, tokenValue, member);
    }
}
