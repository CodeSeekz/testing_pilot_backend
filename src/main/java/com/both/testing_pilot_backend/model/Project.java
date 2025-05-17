package com.both.testing_pilot_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    private UUID projectId;
    private String projectName;
    private String projectDescription;
    private User projectOwner;
    private LocalDateTime createdAt;
}
