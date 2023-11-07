const favor_button = document.getElementById("favor_button");
const cancel_favor_button = document.getElementById("cancel_favor_button");

favor_button.addEventListener("click", addFavorList);
cancel_favor_button.addEventListener("click", removeFavorList);

const lectureId = getLectureId();

function getLectureId() {
    const currentUrl = window.location.href;
    const url = new URL(currentUrl);
    const pathSegments = url.pathname.split("/");
    return pathSegments.pop() || pathSegments.pop();
}

const inputData = {"lectureId" : lectureId};

function addFavorList() {
    $.ajax({
        url:"/favor/add",
        type: "post",
        data: JSON.stringify(inputData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function() {
            location.reload(true);
        },
        error: function(e) {
            alert("찜하기 도중 오류가 발생하였습니다");
            console.log(e);
        }
    })
}

function removeFavorList() {

}

