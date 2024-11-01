package com.ssafy.withme.domain.readme.dto.response;

public record SearchReadMeResponseDTO(
        Long workspace_id,
        String workspace_name,
        String workspace_thumbnail
) {
}
