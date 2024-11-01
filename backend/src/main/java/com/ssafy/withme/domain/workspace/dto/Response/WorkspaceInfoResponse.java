package com.ssafy.withme.domain.workspace.dto.Response;


import com.ssafy.withme.domain.workspace.entity.Workspace;
import lombok.Builder;

public record WorkspaceInfoResponse (
    Long id,
    String name,
    String thumbnail,
    String repoUrl,
    Boolean isCreated,
    String readmeContent,
    Boolean isPrivate
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
                .build();
    }
}
