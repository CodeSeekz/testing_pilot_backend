package com.both.testing_pilot_backend.controller;

import com.both.testing_pilot_backend.model.entity.Project;
import com.both.testing_pilot_backend.model.request.CreateProjectRequest;
import com.both.testing_pilot_backend.model.request.PageRequest;
import com.both.testing_pilot_backend.model.response.ApiResponse;
import com.both.testing_pilot_backend.model.response.CursorPaginationResponse;
import com.both.testing_pilot_backend.service.ProjectService;
import com.both.testing_pilot_backend.utils.CursorPaginationUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<CursorPaginationResponse<Project>>> getAllProjects(@RequestParam MultiValueMap<String, String> params,
                                                                      @RequestParam int page,
                                                                      @RequestParam int size) {
        PageRequest pageRequest = new PageRequest(page, size, 0l);
        List<Project> projects =  projectService.getAllProjects(params, pageRequest);

        CursorPaginationResponse<Project> cursorResponse = CursorPaginationUtil.build(projects, pageRequest.getSize(),
                project -> project.getCreatedAt());

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Projects has been fetched successfully")
                .status(HttpStatus.OK)
                .success(true)
                .data(cursorResponse)
                .build();

        return ResponseEntity.ok(apiResponse);
    };


    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody CreateProjectRequest request) {

        return ResponseEntity.ok().body(projectService.saveProject(request));
    }

    @PreAuthorize("@projectSecurity.isProjectOwner(#projectId)")
    @DeleteMapping("/{project-id}")
    public ResponseEntity<ApiResponse<?>> deleteProject(@PathVariable("project-id") UUID projectId) {
        projectService.deleteProject(projectId);

        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .message("Project has been deleted")
                .status(HttpStatus.NO_CONTENT)
                .success(true)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
