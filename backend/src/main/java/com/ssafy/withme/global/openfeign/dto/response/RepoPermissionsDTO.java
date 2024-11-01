package com.ssafy.withme.global.openfeign.dto.response;

public record RepoPermissionsDTO(
        Boolean admin,
        Boolean push,
        Boolean pull
) {
}
