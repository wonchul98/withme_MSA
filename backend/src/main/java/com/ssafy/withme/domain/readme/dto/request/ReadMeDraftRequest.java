package com.ssafy.withme.domain.readme.dto.request;

public record ReadMeDraftRequest(
        String repository_url,
        String section_name,
        String user_prompt
) {
}
