package com.ssafy.withme.domain.workspace.service;

import com.ssafy.withme.domain.workspace.dto.Response.IntegratedWorkspaceResponse;
import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface WorkspaceService {

    IntegratedWorkspaceResponse makeVisible(String repositoryUrl);

    Slice<WorkspaceInfoResponse> getMyVisibleWorkspaces(Pageable pageable, LocalDateTime cursor);

    List<WorkspaceInfoResponse> getMyInvisibleWorkspaces();

    IntegratedWorkspaceResponse makeInvisible(String repositoryUrl);

    Map<String, List<WorkspaceInfoResponse>> refreshWorkspace();
}
