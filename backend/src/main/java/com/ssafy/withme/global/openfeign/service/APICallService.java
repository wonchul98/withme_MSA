package com.ssafy.withme.global.openfeign.service;

import com.ssafy.withme.global.openfeign.dto.response.DetailResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.RepoResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.UserResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.refined.RefinedRepoDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface APICallService {
    UserResponseDTO GetAuthenticatedUser(String userToken);

    List<RefinedRepoDTO> GetAuthenticatedUserRepos(String userToken);


    @Async
    CompletableFuture<List<DetailResponseDTO>> getRepoDetailsAsync(String userToken, String owner, String repo, String path);
}
