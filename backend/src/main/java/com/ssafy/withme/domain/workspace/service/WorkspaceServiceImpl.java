package com.ssafy.withme.domain.workspace.service;

import com.ssafy.withme.domain.repository.entity.Repo;
import com.ssafy.withme.domain.repository.repository.RepoRepository;
import com.ssafy.withme.domain.workspace.dto.Response.IntegratedWorkspaceResponse;
import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import com.ssafy.withme.global.exception.BusinessException;
import com.ssafy.withme.global.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Security;
import java.util.List;

import java.time.LocalDateTime;

import static com.ssafy.withme.global.consts.StaticConst.*;
import static org.springframework.data.domain.Sort.Direction.*;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService {

    private final RepoRepository repoRepository;
    private final EntityManager entityManager;

    @Override
    public IntegratedWorkspaceResponse makeVisible(String repositoryUrl) {
        return changeVisibility(repositoryUrl, true);
    }

    @Override
    public IntegratedWorkspaceResponse makeInvisible(String repositoryUrl) {
        return changeVisibility(repositoryUrl, false);
    }

    private IntegratedWorkspaceResponse changeVisibility(String repositoryUrl, boolean isVisible) {
        Long memberId = 1L;
        Repo repository = repoRepository.findByMember_IdAndWorkspace_RepoUrl(memberId, repositoryUrl)
                .orElseThrow(() -> new BusinessException(ErrorCode.REPO_NOT_FOUND));
        repository.changeIsVisible(isVisible);

        entityManager.flush();

        // 변경 후의 워크스페이스 리스트 반환
        Pageable pageable = PageRequest.of(PAGEABLE_DEFAULT_PAGE, PAGEABLE_DEFAULT_SIZE, Sort.by(DESC, "updatedAt"));
        Slice<WorkspaceInfoResponse> visibleWorkspaces = getMyVisibleWorkspaces(pageable, LocalDateTime.now());
        List<WorkspaceInfoResponse> invisibleWorkspaces = getMyInvisibleWorkspaces();
        return IntegratedWorkspaceResponse.from(visibleWorkspaces, invisibleWorkspaces);
    }

    @Override
    public Slice<WorkspaceInfoResponse> getMyVisibleWorkspaces(Pageable pageable, LocalDateTime cursor) {
        Long memberId = 1L; // TODO: 실제 멤버 ID로 변경
        if (cursor == null) cursor = LocalDateTime.now();

        return repoRepository.findAllByMember_IdAndIsVisibleTrueAndUpdatedAtBefore(memberId, cursor, pageable)
                .map(visibleRepository -> WorkspaceInfoResponse.from(visibleRepository.getWorkspace()));
    }

    @Override
    public List<WorkspaceInfoResponse> getMyInvisibleWorkspaces() {
        Long memberId = 1L; // TODO: 실제 멤버 ID로 변경
        return repoRepository.findAllByMember_IdAndIsVisibleFalse(memberId).stream()
                .map(repository -> WorkspaceInfoResponse.from(repository.getWorkspace()))
                .toList();
    }
}
