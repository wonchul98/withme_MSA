package com.ssafy.withme.global.openfeign;

import com.ssafy.withme.global.openfeign.dto.response.RepoResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name="GithubAPI", url="https://api.github.com", configuration = FeignGithubConfig.class)
public interface FeignGithubAPIClient {

    @GetMapping("/user")
    UserResponseDTO GetAuthenticatedUser(@RequestHeader("Authorization") String userToken);

    @GetMapping("/user/repos")
    List<RepoResponseDTO> GetAuthenticatedUserRepos(@RequestHeader("Authorization") String userToken);
}
