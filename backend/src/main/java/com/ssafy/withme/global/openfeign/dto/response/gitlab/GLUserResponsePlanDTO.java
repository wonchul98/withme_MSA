package com.ssafy.withme.global.openfeign.dto.response.gitlab;

public record GLUserResponsePlanDTO(
    String name,
    Integer space,
    Integer private_repos,
    Integer collaborators
) {
}
