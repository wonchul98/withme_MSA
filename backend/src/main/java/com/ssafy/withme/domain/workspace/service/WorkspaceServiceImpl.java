package com.ssafy.withme.domain.workspace.service;

import com.ssafy.withme.domain.repository.entity.Repo;
import com.ssafy.withme.domain.repository.repository.RepoRepository;
import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceServiceImpl implements WorkspaceService{

    final private RepoRepository repoRepository;

    @Override
    public Slice<WorkspaceInfoResponse> getMyWorkspaces(Pageable pageable, Long cursor) {
        //TODO : Security 구현 시 수정
//        Long MemberId = SecurityUtil.getCurrentMemberId();
        Slice<Repo> repositories = repoRepository.findByMember_IdAndIdLessThan(1L, cursor, pageable);
        return repositories.map(repository -> WorkspaceInfoResponse.from(repository.getWorkspace()));
    }
}
