package com.ssafy.withme.domain.readme.controller;

import com.ssafy.withme.domain.readme.dto.request.SaveReadMeRequestDTO;
import com.ssafy.withme.domain.readme.dto.response.GetReadMeResponseDTO;
import com.ssafy.withme.domain.readme.service.ReadMeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
