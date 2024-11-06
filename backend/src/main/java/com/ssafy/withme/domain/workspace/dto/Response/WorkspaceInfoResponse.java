package com.ssafy.withme.domain.workspace.dto.Response;


import com.ssafy.withme.domain.workspace.entity.Workspace;
import lombok.Builder;

import static com.ssafy.withme.global.consts.StaticConst.WORKSPACE_ROOM_ID_PREFIX;

public record WorkspaceInfoResponse (
    Long id,
    String name,
    String thumbnail,
    String repoUrl,
    Boolean isCreated,
    String readmeContent,
    Boolean isPrivate,
    String roomId
){
    @Builder
    public WorkspaceInfoResponse{
    }

    public static WorkspaceInfoResponse from(Workspace workspace) {
        return WorkspaceInfoResponse.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .thumbnail(workspace.getThumbnail())
                .repoUrl(workspace.getRepoUrl())
                .isCreated(workspace.getIsCreated())
                .readmeContent(workspace.getReadmeContent())
                .isPrivate(workspace.getIsPrivate())
                .roomId(WORKSPACE_ROOM_ID_PREFIX + workspace.getRoomId())
                .build();
    }
}
