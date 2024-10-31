package com.ssafy.withme.domain.workspace.dto.Response;


import com.ssafy.withme.domain.workspace.entity.Workspace;

public record WorkspaceInfoResponse (
    Long id,
    String name,
    String thumbnail,
    String repoUrl,
    Boolean isCreated,
    String readmeContent,
    Boolean isPrivate
){
    public static WorkspaceInfoResponse from(Workspace workspace) {
        return new WorkspaceInfoResponse(
                workspace.getId(),
                workspace.getName(),
                workspace.getThumbnail(),
                workspace.getRepoUrl(),
                workspace.getIsCreated(),
                workspace.getReadmeContent(),
                workspace.getIsPrivate()
        );
    }
}
