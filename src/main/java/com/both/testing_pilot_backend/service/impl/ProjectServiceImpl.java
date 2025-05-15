package com.both.testing_pilot_backend.service.impl;

import com.both.testing_pilot_backend.model.entity.Project;
import com.both.testing_pilot_backend.model.request.CreateProjectRequest;
import com.both.testing_pilot_backend.repository.ProjectRepository;
import com.both.testing_pilot_backend.service.ProjectService;
import com.both.testing_pilot_backend.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final AuthUtils authUtils;

    @Override
    public Project saveProject(CreateProjectRequest request) {
        System.out.println("auth utile information: " + authUtils.getUserDetails().toString());
        Project project = Project.builder().projectName(request.getProjectName()).projectDescription(request.getProjectDescription()).build();
        return projectRepository.saveProject(project, authUtils.getUserDetails().getUserId());
    }

    @Override
    public boolean isProjectOwner(UUID projectId, UUID userId) {
        return projectRepository.isProjectOwner(projectId, userId);
    }

    @Override
    public void deleteProject(UUID projectId) {
        projectRepository.deleteByProjectId(projectId);
    }

}
