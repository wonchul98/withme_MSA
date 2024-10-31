package com.ssafy.withme.domain.workspace.service;

import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface WorkspaceService {
    Slice<WorkspaceInfoResponse> getMyWorkspaces(Pageable pageable, Long cursor);
}
