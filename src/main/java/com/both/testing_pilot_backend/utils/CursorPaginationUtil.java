package com.both.testing_pilot_backend.utils;

import com.both.testing_pilot_backend.dto.response.CursorPaginationMeta;
import com.both.testing_pilot_backend.dto.response.CursorPaginationResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;

public class CursorPaginationUtil {

    public static <T> CursorPaginationResponse<T> build(List<T> itemsPlusOne, int limit, Function<T, LocalDateTime> cursorExtractor) {

        System.out.println("itemsPlusOne: " + itemsPlusOne.size() + " limit: " + limit);
        // step 1: check if has next
        boolean hasNext = itemsPlusOne.size() > limit;

        // step2: extract payload
        List<T> payload = hasNext ? itemsPlusOne.subList(0, limit) : itemsPlusOne;

        // step3: check cursor
        String nextCursor = null;
        if(hasNext && !payload.isEmpty()) {
            // give the 10th element for the last cursor date
            nextCursor = encodeCursor(cursorExtractor.apply(payload.get(payload.size() - 1)));
        }

        CursorPaginationMeta meta = CursorPaginationMeta.builder()
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .limit(limit)
                .build();

        return CursorPaginationResponse.<T>builder()
                .payload(payload)
                .metadata(meta)
                .build();
    }

    private static String encodeCursor(LocalDateTime createdAt) {
        String cursor = createdAt.toString();
        return Base64.getEncoder().encodeToString(cursor.getBytes());
    }

    public static String decodeCursor(String encodedCursor) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCursor);
        return new String(decodedBytes);
    }

    @Data
    public static class Pair<K, V> {
        private final K left;
        private final V right;

        public Pair(K left, V right) {
            this.left = left;
            this.right = right;
        }
    }
}
