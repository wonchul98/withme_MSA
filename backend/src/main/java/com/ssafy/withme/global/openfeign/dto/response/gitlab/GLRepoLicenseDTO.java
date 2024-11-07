package com.ssafy.withme.global.openfeign.dto.response.gitlab;

public record GLRepoLicenseDTO(
        String key,
        String name,
        String url,
        String spdx_id,
        String node_id,
        String html_url
) {
}
