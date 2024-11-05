package com.ssafy.withme.global.openfeign.service;

import com.ssafy.withme.global.openfeign.dto.response.UserResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.refined.RefinedRepoDTO;
import com.ssafy.withme.global.openfeign.dto.response.refined.RefinedRepoDetailDTO;

import java.util.List;

public interface APICallService {
    UserResponseDTO GetAuthenticatedUser(String userToken);

    List<RefinedRepoDTO> GetAuthenticatedUserRepos(String userToken);

    List<RefinedRepoDetailDTO> getRepoDetails(String userToken, String owner, String repo, String path);
}
