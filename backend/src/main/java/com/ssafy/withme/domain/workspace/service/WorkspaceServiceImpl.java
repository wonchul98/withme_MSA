package com.ssafy.withme.domain.workspace.service;

import com.ssafy.withme.domain.repository.entity.Repo;
import com.ssafy.withme.domain.repository.repository.RepoRepository;
import com.ssafy.withme.domain.workspace.dto.Response.IntegratedWorkspaceResponse;
import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import static com.ssafy.withme.global.consts.StaticConst.PAGEABLE_DEAFULT_PAGE;
import static com.ssafy.withme.global.consts.StaticConst.PAGEABLE_DEFAULT_SIZE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceServiceImpl implements WorkspaceService{

    final private RepoRepository repoRepository;

    @Override
    @Transactional
    public IntegratedWorkspaceResponse makeVisible(String repositoryUrl) {
        // 변경하려는 workspace에 대해 로그인 유저의 권한이 있는지 확인
        Long memberId = 1L; // TODO : 실제 멤버 ID로 변경
        Repo repository = repoRepository.findByMember_IdAndWorkspace_RepoUrl(memberId, repositoryUrl);
        if(repository == null){
            return null; // TODO : 커스텀 에러 구현하고 에러 던지도록 수정 
        }
        repository.changeIsVisible(true);

        // 변경 후의 workspace 결과 반환
        Pageable pageable = PageRequest.of(PAGEABLE_DEFAULT_SIZE, PAGEABLE_DEAFULT_PAGE, Sort.by(Sort.Direction.DESC, "updatedAt"));
        Slice<WorkspaceInfoResponse> visibleWorkspaces = getMyVisibleWorkspaces(pageable, LocalDateTime.now());
        List<WorkspaceInfoResponse> invisibleWorkspaces = getMyInvisibleWorkspaces();

        return IntegratedWorkspaceResponse.from(visibleWorkspaces, invisibleWorkspaces);
    }

    @Override
    public Slice<WorkspaceInfoResponse> getMyVisibleWorkspaces(Pageable pageable, LocalDateTime cursor) {
        Long memberId = 1L; // TODO: 실제 멤버 ID로 변경
        if (cursor == null) cursor = LocalDateTime.now();

        return repoRepository.findByMember_IdAndIsVisibleTrueAndUpdatedAtBefore(memberId, cursor, pageable)
                .map(visibleRepository -> WorkspaceInfoResponse.from(visibleRepository.getWorkspace()));
    }

    @Override
    public List<WorkspaceInfoResponse> getMyInvisibleWorkspaces() {
        Long memberId = 1L; // TODO: 실제 멤버 ID로 변경
        return repoRepository.findByMember_IdAndIsVisibleFalse(memberId).stream()
                .map(repository -> WorkspaceInfoResponse.from(repository.getWorkspace()))
                .toList();
    }
}
