package com.ssafy.withme.domain.workspace.dto.Response;


import com.ssafy.withme.domain.repository.entity.Repo;
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
