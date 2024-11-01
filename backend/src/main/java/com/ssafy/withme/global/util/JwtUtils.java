package com.ssafy.withme.global.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.expires-in.access}")
    private long expiresIn;

    private final SecretKeySpec key;

    public String issueToken(String token) {
        log.info("issueToken with accessToken: {}", token);
        return generateToken(Map.of("token", token));
    }

    private String generateToken(Map<String, String> params){
        Date now = new Date();
        Date expiredDate = Date.from(Instant.now().plus(expiresIn, ChronoUnit.SECONDS));

        JwtBuilder builder = Jwts.builder();

        for(Map.Entry<String, String> entry : params.entrySet())
            builder.claim(entry.getKey(), entry.getValue());

        return builder.issuedAt(now)
                .expiration(expiredDate)
                .signWith(key)
                .compact();
    }
}