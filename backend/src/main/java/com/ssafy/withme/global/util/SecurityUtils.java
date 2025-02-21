package com.ssafy.withme.global.util;

import com.ssafy.withme.domain.member.dto.GitToken;
import com.ssafy.withme.domain.member.entity.Member;
import com.ssafy.withme.domain.member.repository.MemberRepository;
import com.ssafy.withme.global.exception.BusinessException;
import com.ssafy.withme.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@Component
public class SecurityUtils {


    private final MemberRepository memberRepository;

    public Long getMemberId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = request.getHeader("X-User-Id");

        if (userId == null || userId.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_ID_TOKEN);
        }

        return Long.valueOf(userId);
    }

    public GitToken getGitToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = request.getHeader("X-User-Id");
        String token = request.getHeader("X-User-Token");

        if (userId == null || token == null) {
            throw new BusinessException(ErrorCode.INVALID_ID_TOKEN);
        }

        Member member = memberRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_ID_TOKEN));

        return new GitToken(member.getProvider(), token);
    }

//    private Jwt getJwt(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication instanceof JwtAuthenticationToken)
//            return ((JwtAuthenticationToken) authentication).getToken();
//
//        return null;
//    }
//
//    public Long getMemberId(){
//        Jwt jwt = getJwt();
//        if(jwt == null || jwt.getClaimAsString("id") == null)
//            throw new BusinessException(ErrorCode.INVALID_ID_TOKEN);
//        return Long.valueOf(jwt.getClaimAsString("id"));
//    }
//
//    public GitToken getGitToken(){
//        Jwt jwt = getJwt();
//        if(jwt == null || jwt.getClaimAsString("id") == null || jwt.getClaimAsString("token") == null)
//            throw new BusinessException(ErrorCode.INVALID_ID_TOKEN);
//
//        Long memberId = Long.valueOf(jwt.getClaimAsString("id"));
//        Member member = memberRepository.findById(memberId).orElseThrow(()->new BusinessException(ErrorCode.INVALID_ID_TOKEN));
//        return new GitToken(member.getProvider(), jwt.getClaimAsString("token"));
//    }
}
