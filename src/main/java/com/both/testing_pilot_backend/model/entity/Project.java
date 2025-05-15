package com.both.testing_pilot_backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
