package com.ssafy.withme.global.openfeign.service;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@AllArgsConstructor
public class APICallServiceImpl implements APICallService {

    private final FeignGithubAPIClient feignGithubAPIClient;

    @Override
    public UserResponseDTO GetAuthenticatedUser(String userToken){
        return feignGithubAPIClient.GetAuthenticatedUser(userToken);
    }

    @Override
    public List<RefinedRepoDTO> GetAuthenticatedUserRepos(String userToken){
        List<RepoResponseDTO> RepoResponseDTOs = feignGithubAPIClient.GetAuthenticatedUserRepos(userToken);

        return RepoResponseDTOs.stream().map(RefinedRepoDTO::new).toList();
    }

    @Override
    public List<RefinedRepoDetailDTO> getRepoDetails(String userToken, String owner, String repo, String path) {
        return getRepoDetailsAsync(userToken, owner, repo, path)
                .thenApply(details -> details.stream().map(RefinedRepoDetailDTO::new).toList())
                .join();
    }

    @Async
    protected CompletableFuture<List<DetailResponseDTO>> getRepoDetailsAsync(String userToken, String owner, String repo, String path) {
        List<DetailResponseDTO> details = feignGithubAPIClient.GetRepoDetails(userToken, owner, repo, path);
        List<DetailResponseDTO> result = new ArrayList<>();

        // 비동기 호출로 "dir" 타입인 경우 다시 재귀적으로 호출
        for (DetailResponseDTO detail : details) {
            if ("dir".equals(detail.type())) {
                result.addAll(getRepoDetailsAsync(userToken, owner, repo, detail.path()).join());
            }else {
                // 파일명이 .md 또는 이미지 파일(jpeg, png, gif 등)인 경우 추가하지 않음
                if (!(detail.name().endsWith(".md") || detail.name().endsWith(".jpeg") ||
                        detail.name().endsWith(".jpg") || detail.name().endsWith(".png") ||
                        detail.name().endsWith(".gif"))) {
                    result.add(detail);
                }
            }
        }

        return CompletableFuture.completedFuture(result);
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
    public void printTree(TreeNode node, String indent) {
        System.out.println(indent + node.detail.name() + " (" + node.detail.type() + ")");
        for (TreeNode child : node.children) {
            printTree(child, indent + "  ");
        }
    }
}
