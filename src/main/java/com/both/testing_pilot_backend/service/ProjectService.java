package com.both.testing_pilot_backend.service;

import com.both.testing_pilot_backend.model.entity.Project;
import com.both.testing_pilot_backend.model.request.CreateProjectRequest;
import com.both.testing_pilot_backend.model.request.PageRequest;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    Project saveProject(CreateProjectRequest project);
    boolean isProjectOwner(UUID projectId, UUID userId);
    void deleteProject(UUID projectId);
    List<Project> getAllProjects(MultiValueMap<String, String> params, PageRequest pageRequest);
}
