package com.ssafy.withme.domain.workspace.controller;

import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import com.ssafy.withme.domain.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspace")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("")
    public Slice<WorkspaceInfoResponse> myWorkspace(Pageable pageable, Long cursor) {
        return workspaceService.getMyWorkspaces(pageable, cursor);
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/refresh")
    public String refresh() {
        return "refresh";
    }

    @PostMapping("")
    public String visible() {
        return "visible";
    }
}
