const fastcampus_check = document.getElementById("fastcampus_check");
const inflearn_check = document.getElementById("inflearn_check");

const search_filter = document.getElementById("search_filter");
const search_sort = document.getElementById("search_sort");
const search_select =document.getElementById("search_select");

fastcampus_check.addEventListener("change", setFilterValue);
inflearn_check.addEventListener("change", setFilterValue);

function setFilterValue() {
    if(!fastcampus_check.checked && inflearn_check.checked) {
        search_filter.value = "inflearn";
    } else if (fastcampus_check.checked && !inflearn_check.checked) {
        search_filter.value = "fastcampus";
    } else {
        search_filter.value = "all";
    }
}

const params = new URLSearchParams(window.location.search);

const searchSort = params.get("searchSort");
const searchSelect = params.get("searchSelect");
const searchFilter = params.get("searchFilter");

function setSelectedValue(id, valueToSet) {
    if(valueToSet !== null || valueToSet.trim().length !== 0) {
        const selectElement = document.getElementById(id);
        selectElement.value = valueToSet;
    }
}

function setSearchFilter() {
    if(searchFilter === "inflearn") { inflearn_check.checked = true }
    if(searchFilter === "fastcampus") { fastcampus_check.checked = true }
}

document.addEventListener('DOMContentLoaded', function() {
    setSelectedValue("search_select", searchSelect);
    setSelectedValue("search_sort", searchSort);
    setSearchFilter();
})
