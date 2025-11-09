package com.example.legacycrm.api;

import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * Generic pagination envelope that mirrors common API pagination fields.
 *
 * @param <T> type of the paginated elements
 */
@Value
@Builder
public class PageResponse<T> {
    List<T> content;
    long totalElements;
    int totalPages;
    int page;
    int size;
    boolean hasNext;
    boolean hasPrevious;
}

