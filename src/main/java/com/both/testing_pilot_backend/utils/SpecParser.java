package com.both.testing_pilot_backend.utils;

import com.both.testing_pilot_backend.model.request.apiFeature.Filter;
import com.both.testing_pilot_backend.model.request.apiFeature.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SpecParser {

    public List<Filter> parseFilters(MultiValueMap<String, String> params) {
            List<Filter> filters = new ArrayList<>();

            params.forEach((key, value) -> {
                if(isNotFilter(key)) return;

                String[] keyPart = key.split("_");

                if(keyPart.length != 2) return;

                try {
                    String filedName = keyPart[0];
                    Filter.Operator operator = Filter.Operator.valueOf(keyPart[1].toUpperCase());
                    String filedValue = String.join(",", value);

                    Filter filter = Filter.builder()
                            .field(filedName)
                            .operator(operator)
                            .value(filedValue)
                            .build();
                    filters.add(filter);
                } catch (IllegalArgumentException e) {
                }

            });

        System.out.println(filters.toString());
        return filters;
    }

    public List<Sort> parseSort(String sortParams) {
        if(sortParams == null || sortParams.isEmpty()) {
            return null;
        }

        return Arrays.stream(sortParams.split(","))
                .map(s -> {
                    Sort sort = new Sort();
                    if(s.startsWith("-")) {
                        sort.setField(s.substring(1));
                        sort.setDirection(Sort.Direction.DESC);
                    } else {
                        sort.setField(s);
                    }
                    return sort;
                }).collect(Collectors.toList());
    }

    public List<Filter> parseSearch(MultiValueMap<String, String> params) {
        if(params == null || params.isEmpty()) {
            return null;
        }

        List<Filter> searchFields = new ArrayList<>();

        for(Map.Entry<String, List<String>> entry: params.entrySet()) {
            String key = entry.getKey();
            if(key.startsWith("search_")) {
                String field = key.substring("search_".length());
                String value = entry.getValue().get(0);

                Filter filter = new Filter(field, Filter.Operator.LIKE, value);

                searchFields.add(filter);
            }
        }

        System.out.println("search fields ++ " + searchFields.toString() + " ++");
        return searchFields;
    }

    private boolean isNotFilter(String key) {
        return key.equals("page") || key.equals("size") || key.equals("sort") || key.equals("search");
    }
}
