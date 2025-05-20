package com.both.testing_pilot_backend.service;

import com.both.testing_pilot_backend.dto.request.ProjectRequest;
import com.both.testing_pilot_backend.model.Project;
import com.both.testing_pilot_backend.dto.request.PageRequest;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    Project saveProject(ProjectRequest project);
    boolean isProjectOwner(UUID projectId, UUID userId);
    void deleteProject(UUID projectId);
    List<Project> getAllProjects(MultiValueMap<String, String> params, PageRequest pageRequest);
    Project findByProjectId(UUID projectId);
    Project updateProjectById(UUID projectId, ProjectRequest request);
}
