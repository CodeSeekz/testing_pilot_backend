package com.both.testing_pilot_backend.security.expression;

import com.both.testing_pilot_backend.service.ProjectService;
import com.both.testing_pilot_backend.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectSecurity {
    private final AuthUtils authUtils;
    private final ProjectService projectService;

    public boolean isProjectOwner(UUID projectId) throws AccessDeniedException {
        UUID userId = authUtils.getUserDetails().getUserId();

        if(!projectService.isProjectOwner(projectId, userId)) {
            throw new AccessDeniedException("You are not authorized to access this resource");
        }

        return true;
    }
}
