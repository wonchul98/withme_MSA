package com.ssafy.withme.domain.readme.service;

import com.ssafy.withme.domain.readme.dto.request.ReadMeDraftRequest;
import com.ssafy.withme.domain.readme.dto.request.SaveReadMeRequestDTO;
import com.ssafy.withme.domain.readme.dto.response.GetReadMeResponseDTO;
import com.ssafy.withme.domain.readme.dto.response.SearchReadMeResponseDTO;

import java.util.List;

public interface ReadMeService {

    String saveReadme(SaveReadMeRequestDTO readMeRequestDTO);

    GetReadMeResponseDTO getReadme(Long workspaceId);

    List<SearchReadMeResponseDTO> searchReadme(String keyword);

    String makeReadMeDraft(ReadMeDraftRequest readMeDraftRequest);
}
