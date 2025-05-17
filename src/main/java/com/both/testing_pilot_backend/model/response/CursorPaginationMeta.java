package com.both.testing_pilot_backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursorPaginationMeta {
    private String nextCursor;
    private boolean hasNext;
    private Integer limit;
}
