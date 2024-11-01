package com.ssafy.withme.domain.readme.service;

import com.ssafy.withme.domain.readme.dto.request.SaveReadMeRequestDTO;
import com.ssafy.withme.domain.workspace.entity.Workspace;
import com.ssafy.withme.domain.workspace.repository.WorkspaceRepository;
import com.ssafy.withme.global.exception.BusinessException;
import com.ssafy.withme.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadMeServiceImpl implements ReadMeService {
    final private WorkspaceRepository workspaceRepository;


    @Override
    public String saveReadme(SaveReadMeRequestDTO readMeRequestDTO) {
        Workspace workspace = workspaceRepository.findById(readMeRequestDTO.workspace_id())
                .orElseThrow(() -> new BusinessException(ErrorCode.WORKSPACE_NOT_FOUND));
        workspace.changeReadmeContent(readMeRequestDTO.readme_content());
        workspaceRepository.save(workspace);
        return "successfully saved readme";
    }
}
