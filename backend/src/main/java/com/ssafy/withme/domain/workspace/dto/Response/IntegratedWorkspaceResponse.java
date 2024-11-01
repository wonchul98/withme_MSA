package com.ssafy.withme.domain.workspace.dto.Response;

import lombok.Builder;
import org.springframework.data.domain.Slice;
import java.util.List;

public record IntegratedWorkspaceResponse(
        Slice<WorkspaceInfoResponse> visibleWorkspaces,
        List<WorkspaceInfoResponse> invisivleWorkspaces
) {
    @Builder
    public IntegratedWorkspaceResponse{
    }

    public static IntegratedWorkspaceResponse from(Slice<WorkspaceInfoResponse> visibleWorkspaces,
                                          List<WorkspaceInfoResponse> invisivleWorkspaces) {
        return IntegratedWorkspaceResponse.builder()
                .visibleWorkspaces(visibleWorkspaces)
                .invisivleWorkspaces(invisivleWorkspaces)
                .build();
    }
}
