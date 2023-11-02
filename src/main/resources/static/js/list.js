const fastcampus_check = document.getElementById("fastcampus_check");
const inflearn_check = document.getElementById("inflearn_check");

const search_filter = document.getElementById("search_filter");

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