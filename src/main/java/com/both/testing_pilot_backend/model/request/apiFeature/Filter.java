package com.both.testing_pilot_backend.model.request.apiFeature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private String field;
    private Operator operator;
    private String value;

    public enum Operator {
        EQ, NE, GT, GTE, LT, LTE, LIKE, IN, NIN
    }
}