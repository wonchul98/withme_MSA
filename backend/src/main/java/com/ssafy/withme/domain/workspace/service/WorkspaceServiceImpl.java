package com.ssafy.withme.domain.workspace.service;

import com.ssafy.withme.domain.member.repository.MemberRepository;
import com.ssafy.withme.domain.repository.entity.Repo;
import com.ssafy.withme.domain.repository.repository.RepoRepository;
import com.ssafy.withme.domain.workspace.dto.Response.IntegratedWorkspaceResponse;
import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import com.ssafy.withme.domain.workspace.entity.Workspace;
import com.ssafy.withme.domain.workspace.repository.WorkspaceRepository;
import com.ssafy.withme.global.exception.BusinessException;
import com.ssafy.withme.global.exception.ErrorCode;
import com.ssafy.withme.global.util.SecurityUtils;
import com.ssafy.withme.global.openfeign.dto.response.refined.RefinedRepoDTO;
import com.ssafy.withme.global.openfeign.service.APICallService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Security;
import java.util.List;
import java.util.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.ssafy.withme.global.consts.StaticConst.*;
import static org.springframework.data.domain.Sort.Direction.*;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService {

    private final RepoRepository repoRepository;
    private final EntityManager entityManager;
    private final SecurityUtils securityUtils;
    private final APICallService apiCallService;
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;

    @Override
    public IntegratedWorkspaceResponse makeVisible(String repositoryUrl) {
        return changeVisibility(repositoryUrl, true);
    }

    @Override
    public IntegratedWorkspaceResponse makeInvisible(String repositoryUrl) {
        return changeVisibility(repositoryUrl, false);
    }

    private IntegratedWorkspaceResponse changeVisibility(String repositoryUrl, boolean isVisible) {
        Long memberId = securityUtils.getMemberId();
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


    // workspace : 공통, repo : 개인
    @Override
    public Map<String, List<WorkspaceInfoResponse>> refreshWorkspace() {
        Long memberId = securityUtils.getMemberId();
        String memberToken = securityUtils.getGitToken().getTokenValue();

        List<Repo> existingRepos = repoRepository.findAllByMember_Id(memberId);
        List<RefinedRepoDTO> refinedRepos = apiCallService.GetAuthenticatedUserRepos(memberToken);

        updateExistingRepos(existingRepos, refinedRepos);
        createNewRepos(memberId, refinedRepos, existingRepos);

        return classifyWorkspacesByVisibility(memberId);
    }

    // 기존 Repo 갱신
    private void updateExistingRepos(List<Repo> existingRepos, List<RefinedRepoDTO> refinedRepos) {
        Set<String> existingRepoUrls = extractRepoUrls(existingRepos);

        for (RefinedRepoDTO refinedRepo : refinedRepos) {
            String refinedRepoUrl = refinedRepo.htmlUrl();
            existingRepos.stream()
                    .filter(repo -> repo.getWorkspace().getRepoUrl().equals(refinedRepoUrl))
                    .findFirst()
                    .ifPresent(matchedRepo -> {
                        matchedRepo.getWorkspace().changeName(refinedRepo.name());
                        repoRepository.save(matchedRepo);
                    });
        }
    }

    // 새 Repo 생성
    private void createNewRepos(Long memberId, List<RefinedRepoDTO> refinedRepos, List<Repo> existingRepos) {
        Set<String> existingRepoUrls = extractRepoUrls(existingRepos);

        for (RefinedRepoDTO refinedRepo : refinedRepos) {
            String refinedRepoUrl = refinedRepo.htmlUrl();
            if (!existingRepoUrls.contains(refinedRepoUrl)) {
                Workspace newWorkspace = workspaceRepository.findByRepoUrl(refinedRepoUrl);
                if(newWorkspace == null) {
                    newWorkspace = new Workspace(refinedRepo.name(), refinedRepoUrl, null);
                }
                Repo newRepo = new Repo(
                        memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_ID_TOKEN)),
                        newWorkspace
                );
                workspaceRepository.save(newWorkspace);
                repoRepository.save(newRepo);
            }
        }
    }

    // Repo URL 추출
    private Set<String> extractRepoUrls(List<Repo> repos) {
        return repos.stream()
                .map(repo -> repo.getWorkspace().getRepoUrl())
                .collect(Collectors.toSet());
    }

    // 가시성에 따라 워크스페이스 분류
    private Map<String, List<WorkspaceInfoResponse>> classifyWorkspacesByVisibility(Long memberId) {
        List<WorkspaceInfoResponse> visibleWorkspaces = repoRepository.findAllByMember_IdAndIsVisibleTrue(memberId)
                .stream()
                .map(WorkspaceInfoResponse::from)
                .toList();

        List<WorkspaceInfoResponse> invisibleWorkspaces = repoRepository.findAllByMember_IdAndIsVisibleFalse(memberId)
                .stream()
                .map(WorkspaceInfoResponse::from)
                .toList();

        Map<String, List<WorkspaceInfoResponse>> resultMap = new HashMap<>();
        resultMap.put("visible", visibleWorkspaces);
        resultMap.put("invisible", invisibleWorkspaces);
        return resultMap;
    }

}
