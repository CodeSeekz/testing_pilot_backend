package com.both.testing_pilot_backend.utils;

import com.both.testing_pilot_backend.exceptions.BadRequestException;
import com.both.testing_pilot_backend.model.request.apiFeature.Filter;
import com.both.testing_pilot_backend.model.request.apiFeature.Sort;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqlFieldValidator {

    private static final Map<String, Set<String>> ALLOWED_FIELDS = Map.of(
            "projects", Set.of("id", "name", "description", "created_at")
    );

    public  static void validate(String table, List<Filter> filters, List<Sort> sorts) {
        Set<String> fields = ALLOWED_FIELDS.get(table);
        if (fields == null) throw new BadRequestException("Unknown table: " + table);

        if (filters != null) {
            for (Filter filter : filters) {
                if (!fields.contains(filter.getField())) {
                    throw new BadRequestException("Invalid filter field: " + filter.getField());
                }
            }
        }

        if (sorts != null) {
            for (Sort sort : sorts) {
                if (!fields.contains(sort.getField())) {
                    throw new BadRequestException("Invalid sort field: " + sort.getField());
                }
            }
        }
    }

}
