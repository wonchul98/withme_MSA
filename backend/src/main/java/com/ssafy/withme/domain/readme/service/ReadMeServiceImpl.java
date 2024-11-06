package com.ssafy.withme.domain.readme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.ssafy.withme.domain.member.dto.GitToken;
import com.ssafy.withme.domain.member.entity.Member;
import com.ssafy.withme.domain.member.repository.MemberRepository;
import com.ssafy.withme.domain.readme.dto.request.ChatGptRequest;
import com.ssafy.withme.domain.readme.dto.request.ReadMeDraftRequest;
import com.ssafy.withme.domain.readme.dto.request.SaveReadMeRequestDTO;
import com.ssafy.withme.domain.readme.dto.response.GetReadMeResponseDTO;
import com.ssafy.withme.domain.readme.dto.response.SearchReadMeResponseDTO;
import com.ssafy.withme.domain.workspace.entity.Workspace;
import com.ssafy.withme.domain.workspace.repository.WorkspaceRepository;
import com.ssafy.withme.global.exception.BusinessException;
import com.ssafy.withme.global.exception.ErrorCode;
import com.ssafy.withme.global.openfeign.dto.response.refined.RefinedRepoDetailDTO;
import com.ssafy.withme.global.openfeign.service.APICallService;
import com.ssafy.withme.global.openfeign.service.APICallServiceImpl.TreeNode;
import com.ssafy.withme.global.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;



@Service
@RequiredArgsConstructor
public class ReadMeServiceImpl implements ReadMeService {

    private final WorkspaceRepository workspaceRepository;
    private final WebClient webClient;
    private final SecurityUtils securityUtils;
    private final APICallService apiCallService;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE );
    private final MemberRepository memberRepository;

    @Override
    public String saveReadme(SaveReadMeRequestDTO readMeRequestDTO) {
        Workspace workspace = workspaceRepository.findById(readMeRequestDTO.workspace_id())
                .orElseThrow(() -> new BusinessException(ErrorCode.WORKSPACE_NOT_FOUND));
        workspace.changeReadmeContent(readMeRequestDTO.readme_content());
        workspaceRepository.save(workspace);
        return "successfully saved readme";
    }

    @Override
    public GetReadMeResponseDTO getReadme(Long workspace_id) {
        Workspace workspace = workspaceRepository.findById(workspace_id)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORKSPACE_NOT_FOUND));
        return new GetReadMeResponseDTO(workspace.getReadmeContent());
    }

    @Override
    public List<SearchReadMeResponseDTO> searchReadme(String keyword) {
        return workspaceRepository.searchByReadMeContent(keyword).stream()
                .map(workspace -> new SearchReadMeResponseDTO(
                        workspace.getId(),
                        workspace.getName(),
                        workspace.getReadmeContent()
                ))
                .toList();
    }

    @Override
    public Flux<String> makeReadMeDraft(ReadMeDraftRequest readMeDraftRequest) throws JsonProcessingException {

        String message = readMeDraftRequest.userPrompt();
        String repoTreeStructure = getRepoTree(readMeDraftRequest);
        String prompt = makePrompt(readMeDraftRequest.sectionName(), repoTreeStructure, message);

        ChatGptRequest chatGptRequest = ChatGptRequest.of(prompt);
        String requestValue = objectMapper.writeValueAsString(chatGptRequest);

        return webClient.post()
                .uri("/v1/chat/completions")
                .bodyValue(requestValue)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .onErrorResume(error -> {
                    System.err.println("Error during GPT request: " + error.getMessage());
                    return Flux.error(new RuntimeException("Failed to generate ReadMe draft."));
                });
    }

    private static String makePrompt(String sectionName, String repoTreeStructure, String message) {

        return String.format("""
        Below is the file structure of a GitHub repository and a specific section where we want to generate the content for the README file.
        Based on the file structure and section provided, please draft suitable content for that section.
    
        Repository Structure:
        %s
    
        Section: %s
    
        Instructions:
        - If the section is "Introduction", provide a summary of the repository based on the structure.
        - If the section is "Core Technologies", identify the main technologies used based on the filenames or directories.
        - If the section is "Features", list out potential features that might be inferred from the repository structure.
        - Use a formal tone, and aim to provide content that could fit directly into a README file.
    
        Prompt:
        %s
        """, repoTreeStructure, sectionName, message);
    }

    private String getRepoTree(ReadMeDraftRequest readMeDraftRequest) {
        GitToken userToken = securityUtils.getGitToken();
        Member currentMember = memberRepository.findById(securityUtils.getMemberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<RefinedRepoDetailDTO> repoItems = apiCallService.getRepoDetails(
                userToken,
                currentMember.getUsername(),
                readMeDraftRequest.repositoryUrl(),
                ""
        );
        TreeNode root = apiCallService.buildTree(repoItems);
        String treeStructure = apiCallService.buildTreeString(root, "");
        System.out.println(treeStructure);
        return treeStructure;
    }
}
