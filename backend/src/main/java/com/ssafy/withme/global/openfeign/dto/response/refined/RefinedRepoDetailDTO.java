package com.ssafy.withme.global.openfeign.dto.response.refined;

import com.ssafy.withme.global.openfeign.dto.response.DetailResponseDTO;

public record RefinedRepoDetailDTO(
        String name,
        String path,
        String type
) {
    public RefinedRepoDetailDTO(DetailResponseDTO detailResponseDTO) {
        this(
                detailResponseDTO.name(),
                detailResponseDTO.path(),
                detailResponseDTO.type()
        );
    }
}
