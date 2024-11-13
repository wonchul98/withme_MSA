package com.ssafy.withme.domain.workspace.dto.Response;


import com.ssafy.withme.domain.repository.entity.Repo;
import com.ssafy.withme.domain.workspace.entity.Workspace;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.ssafy.withme.global.consts.StaticConst.WORKSPACE_ROOM_ID_PREFIX;

public record WorkspaceInfoResponse (
    Long id,
    String name,
    String owner,
    String thumbnail,
    String repoUrl,
    Boolean isCreated,
    String readmeContent,
    Boolean isPrivate,
    String roomId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
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

    public static WorkspaceInfoResponse from(Repo repo) {
        return WorkspaceInfoResponse.builder()
                .id(repo.getWorkspace().getId())
                .name(repo.getWorkspace().getName())
                .thumbnail(repo.getWorkspace().getThumbnail())
                .repoUrl(repo.getWorkspace().getRepoUrl())
                .isCreated(repo.getWorkspace().getIsCreated())
                .readmeContent(repo.getWorkspace().getReadmeContent())
                .isPrivate(repo.getWorkspace().getIsPrivate())
                .build();
    }
}
