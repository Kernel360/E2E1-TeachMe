package kr.kernel.teachme.domain.lecture.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchRequestTest {

    @DisplayName("SearchRequest 객체의 기본 값이 잘 설정되는지")
    @Test
    void testSearchRequestDefaults() {
        SearchRequest searchRequest = new SearchRequest();

        assertEquals("all", searchRequest.getSearchFilter());
        assertEquals("updateDate", searchRequest.getSearchSort());
        assertEquals("title", searchRequest.getSearchSelect());
        assertEquals("", searchRequest.getSearchInput());
    }

    @DisplayName("SearchRequest 객체에 값이 잘 들어가는지")
    @Test
    void testSearchRequestSetters() {
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.setSearchFilter("newFilter");
        assertEquals("newFilter", searchRequest.getSearchFilter());

        searchRequest.setSearchFilter("");
        assertEquals("all", searchRequest.getSearchFilter());

        searchRequest.setSearchSort("name");
        assertEquals("name", searchRequest.getSearchSort());

        searchRequest.setSearchSelect("description");
        assertEquals("description", searchRequest.getSearchSelect());

        searchRequest.setSearchInput("inputTest");
        assertEquals("inputTest", searchRequest.getSearchInput());
    }
}
