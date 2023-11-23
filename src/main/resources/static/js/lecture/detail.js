function getLectureId() {
    const currentUrl = window.location.href;
    const url = new URL(currentUrl);
    const pathSegments = url.pathname.split("/");
    return pathSegments.pop() || pathSegments.pop();
}

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function isLoggedIn() {
    return getCookie('JWT-AUTHENTICATION') !== undefined;
}

function addFavorList() {
    if (!isLoggedIn()) {
        window.location.href = '/login';
        return;
    }

    const inputData = {"lectureId" : getLectureId()};

    $.ajax({
        url:"/favor/add",
        type: "post",
        data: JSON.stringify(inputData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function() {
            location.reload(true);
        },
        error: function(xhr) {
            if(xhr.status === 401) {
                window.location.href = '/login';
            } else {
                alert("찜하기 도중 오류가 발생하였습니다");
                console.log(xhr);
            }

        }
    })
}

function removeFavorList() {
    const inputData = {"lectureId" : getLectureId()};

    $.ajax({
        url:"/favor/delete",
        type: "delete",
        data: JSON.stringify(inputData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function() {
            location.reload(true);
        },
        error: function(e) {
            alert("찜 취소 도중 오류가 발생했습니다");
            console.log(e);
        }
    })
}

const rating_input = document.querySelector('.rating input');
const rating_star = document.querySelector('.rating_star');

rating_input.addEventListener('change', () => {
    rating_star.style.width = `${rating_input.value * 10}%`;
});

const review_text= document.getElementById("review_text");
const review_button = document.getElementById("review_button");

review_button.addEventListener("click", () => {
    if (!isLoggedIn()) {
        window.location.href = '/login';
        return;
    }

    if(review_text.value === "") {
        alert("리뷰 내용을 작성해주세요");
        return;
    }

    if(rating_input.value == 0) {
        alert("리뷰 별점을 작성해주세요");
        return;
    }

    const reviewData = {
        "content" : review_text.value,
        "score" : rating_input.value / 2,
        "lectureId" : getLectureId()
    };

    $.ajax({
        url:"/review/add",
        type: "post",
        data: JSON.stringify(reviewData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function() {
            location.reload(true);
        },
        error: function(e) {
            alert("리뷰 작성 중 오류가 발생하였습니다");
            console.log(e);
        }
    })

});



