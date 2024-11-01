package com.ssafy.withme.domain.readme.controller;

import com.ssafy.withme.domain.readme.dto.request.SaveReadMeRequestDTO;
import com.ssafy.withme.domain.readme.dto.response.GetReadMeResponseDTO;
import com.ssafy.withme.domain.readme.dto.response.SearchReadMeResponseDTO;
import com.ssafy.withme.domain.readme.service.ReadMeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workspace/readme")
public class ReadMeController {
    private final ReadMeService readMeService;

    @PostMapping("")
    public String saveReadMe(SaveReadMeRequestDTO readMeRequestDTO) {
        return readMeService.saveReadme(readMeRequestDTO);
    }

    @GetMapping("/{workspace_id}")
    public GetReadMeResponseDTO getReadMe(@PathVariable("workspace_id") Long workspaceId) {
        return readMeService.getReadme(workspaceId);
    }

    @GetMapping("/search")
    public List<SearchReadMeResponseDTO> getSearchReadMe(@RequestParam String keyword) {
        return readMeService.searchReadme(keyword);
    }
}
