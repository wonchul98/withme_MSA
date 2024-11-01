package com.ssafy.withme.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id @GeneratedValue
    private Integer id;

    private String name;

    private ProviderType provider;

    /**
     * 깃허브, 깃랩 용
     * */
    private String accessToken;
}
