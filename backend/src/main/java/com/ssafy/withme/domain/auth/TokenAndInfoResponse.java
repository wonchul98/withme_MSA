package com.ssafy.withme.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenAndInfoResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("name")
    private String userName;

    @JsonProperty("image_url")
    private String imageUrl;
}
