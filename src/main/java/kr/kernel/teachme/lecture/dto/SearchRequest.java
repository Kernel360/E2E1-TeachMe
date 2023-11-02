package kr.kernel.teachme.lecture.dto;

import lombok.Data;

@Data
public class SearchRequest {
    private String searchFilter = "all";
    private String searchSort = "updateDate";
    private String searchSelect = "title";
    private String searchInput = "";
}
