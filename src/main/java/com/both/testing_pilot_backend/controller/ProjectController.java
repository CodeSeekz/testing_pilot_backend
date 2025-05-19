package com.both.testing_pilot_backend.controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.both.testing_pilot_backend.dto.request.PageRequest;
import com.both.testing_pilot_backend.dto.request.ProjectRequest;
import com.both.testing_pilot_backend.dto.response.CustomApiResponse;
import com.both.testing_pilot_backend.dto.response.CursorPaginationResponse;
import com.both.testing_pilot_backend.model.Project;
import com.both.testing_pilot_backend.service.ProjectService;
import com.both.testing_pilot_backend.utils.CursorPaginationUtil;

@RestController
@Tag(name = "Project", description = "Operations related to project creation, retrieval, and deletion")
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(
            summary = "Retrieve paginated list of projects",
            description = "Fetches projects with cursor-based pagination. Requires valid query parameters.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved projects"),
                    @ApiResponse(responseCode = "400", description = "Invalid query parameters", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)

            }
    )
    public ResponseEntity<CustomApiResponse<CursorPaginationResponse<Project>>> getAllProjects(@RequestParam MultiValueMap<String, String> params,
                                                                                               @RequestParam(required = false) @Min(0) int page,
                                                                                               @RequestParam @Min(1) int size) {
        PageRequest pageRequest = new PageRequest(page, size, 0l);
        List<Project> projects = projectService.getAllProjects(params, pageRequest);

        CursorPaginationResponse<Project> cursorResponse = CursorPaginationUtil.build(projects,
                pageRequest.getSize(),
                project -> project.getCreatedAt());

        CustomApiResponse apiResponse = CustomApiResponse.builder().message("Projects has been fetched successfully").status(
                HttpStatus.OK).success(true).data(cursorResponse).build();

        return ResponseEntity.ok(apiResponse);
    };

    @GetMapping("/{project-id}")
    @Operation(
            summary = "Get project by ID",
            description = "Fetches a single project by its UUID."
    )
    public ResponseEntity<CustomApiResponse<Project>> getProjectById(@PathVariable("project-id") UUID projectId) {
        Project project = projectService.findByProjectId(projectId);
        CustomApiResponse apiResponse = CustomApiResponse.builder().message("Project has been fetched successfully").status(
                HttpStatus.OK).success(true).data(project).build();

        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping
    @Operation(
            summary = "Create a new project",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Project created successfully"),
                    @ApiResponse(responseCode = "400", description = "Validation errors in request body"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectRequest request) {

        return ResponseEntity.ok().body(projectService.saveProject(request));
    }

    @PreAuthorize("@projectSecurity.isProjectOwner(#projectId)")
    @DeleteMapping("/{project-id}")
    @Operation(
            summary = "Delete a project",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<CustomApiResponse<?>> deleteProject(@PathVariable("project-id") UUID projectId) {
        projectService.deleteProject(projectId);

        CustomApiResponse<Object> apiResponse = CustomApiResponse.builder().message("Project has been deleted").status(HttpStatus.NO_CONTENT).success(
                true).build();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("@projectSecurity.isProjectOwner(#projectId)")
    @PutMapping("/{project-id}")
    @Operation(
            summary = "Update an existing project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Project updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Validation errors in request body"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<CustomApiResponse<?>> updateProjectById(@Valid @RequestBody ProjectRequest request,
                                                                        @PathVariable("project-id") UUID projectId) {
        Project project = projectService.updateProjectById(projectId, request);

        CustomApiResponse<Object> apiResponse = CustomApiResponse.builder().message("Project has been updated successfully").status(
                HttpStatus.OK).success(true).data(project).build();
        return ResponseEntity.ok(apiResponse);
    }
}
