const listStyleChangeStartY = 1300;
const listStyleChangeEndY = 2085;

const listItems = document.querySelectorAll(".list-item");

const division = (listStyleChangeEndY - listStyleChangeStartY) / listItems.length;

window.addEventListener("scroll", () => {
    if(document.getElementById("on")) document.getElementById("on").removeAttribute("id");

    if(window.scrollY > listStyleChangeStartY && window.scrollY < listStyleChangeEndY) {
        const targetIndex = Math.round((window.scrollY - listStyleChangeStartY) /division)

        if(listItems[targetIndex]) listItems[targetIndex].id = "on";
    }
})