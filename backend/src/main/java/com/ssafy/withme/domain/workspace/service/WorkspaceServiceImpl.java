package com.ssafy.withme.domain.workspace.service;

import com.ssafy.withme.domain.repository.repository.RepoRepository;
import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceServiceImpl implements WorkspaceService{

    final private RepoRepository repoRepository;

    @Override
    @Transactional
    public void makeVisible(String repositoryUrl) {
        //권한 확인

        //
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
