package com.both.testing_pilot_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursorPaginationResponse <T> {
    private List<T> payload;
    private CursorPaginationMeta metadata;
}
