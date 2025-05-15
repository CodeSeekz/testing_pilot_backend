package com.both.testing_pilot_backend.controller;

import com.both.testing_pilot_backend.model.request.CreateProjectRequest;
import com.both.testing_pilot_backend.model.response.ApiResponse;
import com.both.testing_pilot_backend.service.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

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
