package kr.kernel.teachme.domain.lecture.dto;

import lombok.Getter;


@Getter
public class SearchRequest {

    private String searchFilter;
    private String searchSort;
    private String searchSelect;
    private String searchInput;

    public SearchRequest() {
        this.searchFilter = "all";
        this.searchSort = "updateDate";
        this.searchSelect = "title";
        this.searchInput = "";
    }

    public void setSearchFilter(String searchFilter) {
        if(searchFilter == null || searchFilter.trim().isEmpty()) {
            this.searchFilter = "all";
        } else {
            this.searchFilter = searchFilter;
        }
    }

    public void setSearchSort(String searchSort) {
        if(searchSort == null || searchSort.trim().isEmpty()) {
            this.searchSort = "updateDate";
        } else {
            this.searchSort = searchSort;
        }
    }

    public void setSearchSelect(String searchSelect) {
        if(searchSelect == null || searchSelect.trim().isEmpty()) {
            this.searchSelect = "title";
        } else {
            this.searchSelect = searchSelect;
        }
    }

    public void setSearchInput(String searchInput) {
        if(searchInput == null || searchInput.trim().isEmpty()) {
            this.searchInput = "";
        } else {
            this.searchInput = searchInput;
        }
    }
}
