package com.ssafy.withme.global.openfeign.service;

import com.ssafy.withme.domain.member.dto.GitToken;
import com.ssafy.withme.global.openfeign.dto.response.UserResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.refined.RefinedRepoDTO;
import com.ssafy.withme.global.openfeign.dto.response.refined.RefinedRepoDetailDTO;

import java.util.List;

public interface APICallService {
    UserResponseDTO GetAuthenticatedUser(GitToken gitToken);

    List<RefinedRepoDTO> GetAuthenticatedUserRepos(GitToken gitToken);

    List<RefinedRepoDetailDTO> getRepoDetails(GitToken gitToken, String owner, String repo, String path);

    // 트리 구조를 생성하는 메소드
    APICallServiceImpl.TreeNode buildTree(List<RefinedRepoDetailDTO> items);

    // 디버깅용 트리 출력 메소드
    String buildTreeString(APICallServiceImpl.TreeNode node, String indent);
}
