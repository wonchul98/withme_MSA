package com.ssafy.withme.domain.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @GetMapping("/info")
    public String info(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaimAsString("token");
    }

    @GetMapping("/info2")
    public String info2(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaimAsString("id");
    }
}
