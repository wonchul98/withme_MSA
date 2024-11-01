package com.ssafy.withme.global.openfeign.dto.response;

public record UserResponsePlanDTO(
    String name,
    Integer space,
    Integer private_repos,
    Integer collaborators
) {
}
