package com.ssafy.withme.domain.readme.service;

import com.ssafy.withme.domain.readme.dto.request.SaveReadMeRequestDTO;
import com.ssafy.withme.domain.readme.dto.response.GetReadMeResponseDTO;

public interface ReadMeService {
    String saveReadme(SaveReadMeRequestDTO readMeRequestDTO);
    GetReadMeResponseDTO getReadme(Long workspaceId);
}
