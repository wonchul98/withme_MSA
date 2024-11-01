package com.ssafy.withme.domain.workspace.controller;

import com.ssafy.withme.domain.workspace.dto.Response.WorkspaceInfoResponse;
import com.ssafy.withme.domain.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspace")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/visible")
    public Slice<WorkspaceInfoResponse> myVisibleWorkspace(@PageableDefault(size = 10, sort = "updatedAt",
            direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) LocalDateTime cursor) {
        return workspaceService.getMyVisibleWorkspaces(pageable, cursor);
    }

    @GetMapping("/invisible")
    public List<WorkspaceInfoResponse> myInvisibleWorkspaces() {
        return workspaceService.getMyInvisibleWorkspaces();
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
    public void visible(@RequestParam String repository_url) {
        workspaceService.makeVisible(repository_url);
    }
}
