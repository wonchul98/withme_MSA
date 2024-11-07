package com.ssafy.withme.global.openfeign.dto.response.gitlab;

public record GLRepoPermissionsDTO(
        Boolean admin,
        Boolean push,
        Boolean pull
) {
}
