package com.ssafy.withme.domain.workspace.controller;

import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import com.ssafy.withme.domain.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspace")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("")
    public Slice<WorkspaceInfoResponse> myWorkspace(@PageableDefault(size = 10, sort = "updatedAt",
            direction = Sort.Direction.DESC) Pageable pageable, LocalDateTime cursor) {
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
