package com.ssafy.withme.global.openfeign.dto.response.gitlab;

public record GLDetailResponseDTO(
        String name,
        String path,
        String sha,
        Integer size,
        String url,
        String html_url,
        String git_url,
        String download_url,
        String type,
        GLDetailLinkDTO _links
) {
}
