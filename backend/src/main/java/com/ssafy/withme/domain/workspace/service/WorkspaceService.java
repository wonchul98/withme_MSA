package com.ssafy.withme.domain.workspace.service;

import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

public interface WorkspaceService {

    Slice<WorkspaceInfoResponse> getMyWorkspaces(Pageable pageable, LocalDateTime cursor);
}
