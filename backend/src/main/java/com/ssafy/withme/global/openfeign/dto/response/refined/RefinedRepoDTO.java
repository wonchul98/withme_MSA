package com.ssafy.withme.global.openfeign.dto.response.refined;

import com.ssafy.withme.global.openfeign.dto.response.RepoResponseDTO;

public record RefinedRepoDTO(
        String name,
        String fullName,
        String htmlUrl,
        String description

) {
    public RefinedRepoDTO(RepoResponseDTO responseDTO) {
        this(
                responseDTO.name(),
                responseDTO.full_name(),
                responseDTO.html_url(),
                responseDTO.description()
        );
    }
}
