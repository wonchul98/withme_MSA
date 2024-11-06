package com.ssafy.withme.domain.workspace.service;

import com.ssafy.withme.domain.repository.entity.Repo;
import com.ssafy.withme.domain.repository.repository.RepoRepository;
import com.ssafy.withme.domain.workspace.dto.Response.IntegratedWorkspaceResponse;
import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import com.ssafy.withme.domain.workspace.entity.Workspace;
import com.ssafy.withme.domain.workspace.repository.WorkspaceRepository;
import com.ssafy.withme.global.exception.BusinessException;
import com.ssafy.withme.global.util.SecurityUtils;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.ssafy.withme.global.consts.StaticConst.*;
import static com.ssafy.withme.global.exception.ErrorCode.*;
import static org.springframework.data.domain.Sort.Direction.*;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService {

    private final RepoRepository repoRepository;
    private final EntityManager entityManager;
    private final SecurityUtils securityUtils;
    private final WorkspaceRepository workspaceRepository;

    @Override
    public IntegratedWorkspaceResponse makeVisible(String repositoryUrl) {
        return changeVisibility(repositoryUrl, true);
    }

    @Override
    public IntegratedWorkspaceResponse makeInvisible(String repositoryUrl) {
        return changeVisibility(repositoryUrl, false);
    }

    @Override
    public IntegratedWorkspaceResponse activeWorkspace(Long workspaceId) {
        // 권한 및 유효성 검사
        Long memberId = securityUtils.getMemberId();
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(()->new BusinessException(WORKSPACE_NOT_FOUND));
        repoRepository.findByMember_IdAndWorkspace_RepoUrl(memberId, workspace.getRepoUrl())
                .orElseThrow(()->new BusinessException(REPO_NOT_ALLOWED));

        // 한번도 활성화가 안된 경우
        if(!workspace.getIsCreated()) {
            long randomId = Math.abs(UUID.randomUUID().getMostSignificantBits()); // TODO : 검증 매서드가 필요하려나
            workspace.changeRoomId(randomId);
            workspace.changeIsCreated(true);
        }

        return changeVisibility(workspace.getRepoUrl(), true);
    }

    @Override
    public Slice<WorkspaceInfoResponse> getMyVisibleWorkspaces(Pageable pageable, LocalDateTime cursor) {
        Long memberId = securityUtils.getMemberId();
        if (cursor == null) cursor = LocalDateTime.now();

        return repoRepository.findAllByMember_IdAndIsVisibleTrueAndUpdatedAtBefore(memberId, cursor, pageable)
                .map(visibleRepository -> WorkspaceInfoResponse.from(visibleRepository.getWorkspace()));
    }

    @Override
    public List<WorkspaceInfoResponse> getMyInvisibleWorkspaces() {
        Long memberId = securityUtils.getMemberId();
        return repoRepository.findAllByMember_IdAndIsVisibleFalse(memberId).stream()
                .map(repository -> WorkspaceInfoResponse.from(repository.getWorkspace()))
                .toList();
    }
    // 현재 로그인 한 유저의 repo를 url기반으로 찾고 visible을 수정한 뒤 그 결과를 반환
    private IntegratedWorkspaceResponse changeVisibility(String repositoryUrl, boolean isVisible) {
        Long memberId = securityUtils.getMemberId();
        Repo repository = repoRepository.findByMember_IdAndWorkspace_RepoUrl(memberId, repositoryUrl)
                .orElseThrow(() -> new BusinessException(REPO_NOT_FOUND));
        repository.changeIsVisible(isVisible);

        entityManager.flush();

        // 변경 후의 워크스페이스 리스트 반환
        Pageable pageable = PageRequest.of(PAGEABLE_DEFAULT_PAGE, PAGEABLE_DEFAULT_SIZE, Sort.by(DESC, "updatedAt"));
        Slice<WorkspaceInfoResponse> visibleWorkspaces = getMyVisibleWorkspaces(pageable, LocalDateTime.now());
        List<WorkspaceInfoResponse> invisibleWorkspaces = getMyInvisibleWorkspaces();
        return IntegratedWorkspaceResponse.from(visibleWorkspaces, invisibleWorkspaces);
    }
}
