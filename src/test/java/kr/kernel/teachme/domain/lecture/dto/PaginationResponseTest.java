package kr.kernel.teachme.domain.lecture.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginationResponseTest {

    @DisplayName("PaginationResponse 객체의 값이 정확히 들어가는지")
    @Test
    void testPaginationResponse() {
        List<String> content = List.of("항목1", "항목2");
        int page = 1;
        int size = 10;
        int totalPages = 2;
        int currentElements = 2;
        Long totalElements = 20L;
        PaginationResponse<List<String>> paginationResponse = PaginationResponse.of(content, page, size, totalPages, currentElements, totalElements);

        assertEquals(content, paginationResponse.getBody());

        Pagination pagination = paginationResponse.getPagination();
        assertEquals(page, pagination.getPage());
        assertEquals(size, pagination.getSize());
        assertEquals(totalPages, pagination.getTotalPage());
        assertEquals(currentElements, pagination.getCurrentElements());
        assertEquals(totalElements, pagination.getTotalElements());
    }
}
