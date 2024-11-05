package com.ssafy.withme.global.openfeign.service;

import com.ssafy.withme.global.openfeign.FeignGithubAPIClient;
import com.ssafy.withme.global.openfeign.dto.response.DetailResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.RepoResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@AllArgsConstructor
public class APICallService {

    private final FeignGithubAPIClient feignGithubAPIClient;

    public UserResponseDTO GetAuthenticatedUser(String userToken){
        return feignGithubAPIClient.GetAuthenticatedUser(userToken);
    }

    public List<RepoResponseDTO> GetAuthenticatedUserRepos(String userToken){
        return feignGithubAPIClient.GetAuthenticatedUserRepos(userToken);
    }

    @Async
    public CompletableFuture<List<DetailResponseDTO>> getRepoDetailsAsync(String userToken, String owner, String repo, String path) {
        List<DetailResponseDTO> details = feignGithubAPIClient.GetRepoDetails(userToken, owner, repo, path);
        List<DetailResponseDTO> result = new ArrayList<>(details);

        // 비동기 호출로 "dir" 타입인 경우 다시 재귀적으로 호출
        for (DetailResponseDTO detail : details) {
            if ("dir".equals(detail.type())) {
                result.addAll(getRepoDetailsAsync(userToken, owner, repo, detail.path()).join());
            }else {
                result.add(detail);
            }
        }

        return CompletableFuture.completedFuture(result);
    }
}
