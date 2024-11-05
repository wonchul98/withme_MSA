package com.ssafy.withme.domain.readme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.ssafy.withme.domain.readme.dto.request.ChatGptRequest;
import com.ssafy.withme.domain.readme.dto.request.ReadMeDraftRequest;
import com.ssafy.withme.domain.readme.dto.request.SaveReadMeRequestDTO;
import com.ssafy.withme.domain.readme.dto.response.GetReadMeResponseDTO;
import com.ssafy.withme.domain.readme.dto.response.SearchReadMeResponseDTO;
import com.ssafy.withme.domain.workspace.entity.Workspace;
import com.ssafy.withme.domain.workspace.repository.WorkspaceRepository;
import com.ssafy.withme.global.config.ChatGptConfig;
import com.ssafy.withme.global.exception.BusinessException;
import com.ssafy.withme.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReadMeServiceImpl implements ReadMeService {

    private final WorkspaceRepository workspaceRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE );

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
        ChatGptRequest chatGptRequest = ChatGptRequest.of(readMeDraftRequest.userPrompt());
        String requestValue = objectMapper.writeValueAsString(chatGptRequest);
        Flux<String> eventStream = webClient.post()
                .uri("/v1/chat/completions")
                .bodyValue(requestValue)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class);
        return eventStream;
    }

    @Override
    public SseEmitter getGptResponse(String prompt) {
        return null;
    }
}
