package com.ssafy.withme.global.openfeign.service;

import com.ssafy.withme.domain.member.dto.GitToken;
import com.ssafy.withme.domain.member.entity.Provider;
import com.ssafy.withme.global.openfeign.FeignGithubAPIClient;
import com.ssafy.withme.global.openfeign.dto.response.DetailResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.RepoResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.UserResponseDTO;
import com.ssafy.withme.global.openfeign.dto.response.refined.RefinedRepoDTO;
import com.ssafy.withme.global.openfeign.dto.response.refined.RefinedRepoDetailDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class APICallServiceImpl implements APICallService {

    private final FeignGithubAPIClient feignGithubAPIClient;
    private final Set<String> visitedPaths = ConcurrentHashMap.newKeySet(); // 중복 요청 방지
    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // 적절한 스레드 풀 설정

    @Override
    public UserResponseDTO GetAuthenticatedUser(GitToken gitToken){
        if(gitToken == null) return null;

        if(gitToken.getProvider().equals(Provider.GITHUB)){
            return feignGithubAPIClient.GetAuthenticatedUser("Bearer " + gitToken.getTokenValue());
        }else{
            // TODO : 깃랩 API 코드 작성
            return null;
        }
    }

    @Override
    public List<RefinedRepoDTO> GetAuthenticatedUserRepos(GitToken gitToken){
        if(gitToken == null) return null;

        if(gitToken.getProvider().equals(Provider.GITHUB)){
            List<RepoResponseDTO> RepoResponseDTOs = feignGithubAPIClient.GetAuthenticatedUserRepos("Bearer " + gitToken.getTokenValue());

            return RepoResponseDTOs.stream().map(RefinedRepoDTO::new).toList();
        }else{
            // TODO : 깃랩 API 코드 작성
            return null;
        }
    }

    @Override
    public List<RefinedRepoDetailDTO> getRepoDetails(GitToken gitToken, String owner, String repo, String path) {
        if(gitToken == null) return null;

        if(gitToken.getProvider().equals(Provider.GITHUB)){
            return getRepoDetails("Bearer " + gitToken.getTokenValue(), owner, repo, path)
                    .thenApply(details -> details.stream().map(RefinedRepoDetailDTO::new).toList())
                    .join();
        }else{
            // TODO : 깃랩 API 코드 작성
            return null;
        }



    }

    public CompletableFuture<List<DetailResponseDTO>> getRepoDetails(String userToken, String owner, String repo, String path) {

        // 이미 방문한 경로는 다시 탐색하지 않음
        if (!visitedPaths.add(path)) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        // 디렉토리 및 파일 리스트 비동기 호출
        CompletableFuture<List<DetailResponseDTO>> detailsFuture = CompletableFuture.supplyAsync(() ->
                feignGithubAPIClient.GetRepoDetails(userToken, owner, repo, path), executorService);

        return detailsFuture.thenCompose(details -> {
            List<CompletableFuture<List<DetailResponseDTO>>> futures = details.parallelStream()
                    .filter(this::shouldProcess) // 필요 없는 파일 필터링
                    .map(detail -> {
                        if ("dir".equals(detail.type())) {
                            // 하위 디렉토리 비동기 탐색
                            return getRepoDetails(userToken, owner, repo, detail.path());
                        } else {
                            return CompletableFuture.completedFuture(List.of(detail)); // 파일은 바로 추가
                        }
                    })
                    .toList();

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> futures.stream()
                            .flatMap(f -> f.join().stream())
                            .collect(Collectors.toList()));
        });
    }

    // 필터링 로직
    private boolean shouldProcess(DetailResponseDTO detail) {
        return !(detail.name().endsWith(".md") || detail.name().endsWith(".jpeg") ||
                detail.name().endsWith(".jpg") || detail.name().endsWith(".png") ||
                detail.name().endsWith(".gif"));
    }


    // Tree Node class with RefinedRepoDetailDTO
    public static class TreeNode {
        RefinedRepoDetailDTO detail;
        List<TreeNode> children;

        public TreeNode(RefinedRepoDetailDTO detail) {
            this.detail = detail;
            this.children = new ArrayList<>();
        }
    }

    @Override
    public TreeNode buildTree(List<RefinedRepoDetailDTO> items) {
        TreeNode root = new TreeNode(new RefinedRepoDetailDTO("root", "", "dir"));
        Map<String, TreeNode> pathMap = new HashMap<>();
        pathMap.put("", root);

        for (RefinedRepoDetailDTO item : items) {
            String[] parts = item.path().split("/");
            StringBuilder currentPath = new StringBuilder();
            TreeNode parent = root;

            for (int i = 0; i < parts.length; i++) {
                currentPath.append(i == 0 ? "" : "/").append(parts[i]);
                String subPath = currentPath.toString();

                // 만약 경로가 아직 맵에 추가되지 않았다면 dir 로서 추가
                if (!pathMap.containsKey(subPath)) {
                    RefinedRepoDetailDTO nodeDetail = new RefinedRepoDetailDTO(
                            parts[i],
                            subPath,
                            i == parts.length - 1 ? item.type() : "dir"  // Last part takes the item's type, others are "dir"
                    );
                    TreeNode node = new TreeNode(nodeDetail);
                    pathMap.put(subPath, node);
                    parent.children.add(node);
                }

                parent = pathMap.get(subPath);
            }
        }

        return root;
    }

    @Override
    public String buildTreeString(TreeNode node, String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append(node.detail.name()).append(" (").append(node.detail.type()).append(")\n");
        for (TreeNode child : node.children) {
            sb.append(buildTreeString(child, indent + "  "));
        }
        return sb.toString();
    }
}
