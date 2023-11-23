const listItems = document.querySelectorAll(".list-item");
const listItemWrapper = document.getElementById("list-item-wrapper");



window.addEventListener("resize", setDynamicPositions);
window.addEventListener("scroll", handleScroll);

function setDynamicPositions() {
    const wrapperRect = listItemWrapper.getBoundingClientRect();
    const windowHeight = window.innerHeight;

    window.listStyleChangeStartY = wrapperRect.top + window.scrollY - (windowHeight / 2);
    window.listStyleChangeEndY = wrapperRect.bottom + window.scrollY - (windowHeight / 2);
}

function handleScroll() {
    if (document.getElementById("on")) document.getElementById("on").removeAttribute("id");

    if (window.scrollY > window.listStyleChangeStartY && window.scrollY < window.listStyleChangeEndY) {
        const division = (window.listStyleChangeEndY - window.listStyleChangeStartY) / listItems.length;
        const targetIndex = Math.round((window.scrollY - window.listStyleChangeStartY) / division);

        if (listItems[targetIndex]) listItems[targetIndex].id = "on";
    }
}

// 최초 로드 시 위치 설정
setDynamicPositions();