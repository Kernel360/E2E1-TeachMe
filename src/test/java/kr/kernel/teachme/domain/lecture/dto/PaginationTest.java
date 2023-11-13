package kr.kernel.teachme.domain.lecture.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginationTest {
    @DisplayName("Pagination 객체의 값이 잘 들어가는지")
    @Test
    void testPaginationDto() {
        Pagination pagination = Pagination.builder()
                .page(1)
                .size(10)
                .currentElements(5)
                .totalPage(20)
                .totalElements(200L)
                .build();

        assertEquals(1, pagination.getPage());

        assertEquals(10, pagination.getSize());

        assertEquals(5, pagination.getCurrentElements());

        assertEquals(20, pagination.getTotalPage());

        assertEquals(200L, pagination.getTotalElements());
    }
}
