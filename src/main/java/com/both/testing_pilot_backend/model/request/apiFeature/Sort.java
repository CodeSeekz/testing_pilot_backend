package com.both.testing_pilot_backend.model.request.apiFeature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sort {
    private String field;
    private Direction direction = Direction.ASC;

    public enum Direction {
        ASC, DESC
    }
}