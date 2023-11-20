let currentReviewId;

function showEditForm(reviewId) {
    const reviewBox = document.createElement("div");
    reviewBox.className = "rating_box";
    reviewBox.innerHTML = "<div class=\"rating\">\n" +
        "                        ★★★★★\n" +
        "                        <span class=\"rating_star\">★★★★★</span>\n" +
        "                        <input id=\"review_score\" type=\"range\" value=\"0\" step=\"1\" min=\"0\" max=\"10\">\n" +
        "                    </div>"

    const reviewLabel = document.createElement("label");
    reviewLabel.style.width = "100%";
    reviewLabel.style.marginBottom = "30px";
    reviewLabel.style.display = "flex";

    reviewLabel.innerHTML = "<textarea id=\"review_text\" placeholder=\"당신의 리뷰를 남겨주세요 ✏️\" class=\"review_area\"></textarea>\n" +
        "                    <button id=\"review_button\" class=\"review_button\" onclick=\"edit(" + reviewId + ")\">\n" +
        "                        <i class=\"fa-solid fa-pen-to-square\"></i>\n" +
        "                    </button>"

    const currentReviewDiv = document.querySelector(".box" + reviewId);
    currentReviewDiv.parentNode.insertBefore(reviewBox, currentReviewDiv.nextSibling);
    reviewBox.parentNode.insertBefore(reviewLabel, reviewBox.nextSibling);

    currentReviewDiv.style.display = "none";

    const rating_input = document.querySelector('.rating input');
    const rating_star = document.querySelector('.rating_star');

    rating_input.addEventListener('change', () => {
        rating_star.style.width = `${rating_input.value * 10}%`;
    });

    currentReviewId = reviewId;
}

function edit() {
    if(currentReviewId === null) {
        alert("리뷰 수정 중 오류가 발생했습니다");
        return;
    }

    const review_text = document.getElementById("review_text");
    const rating_input = document.querySelector('.rating input');

    if(review_text.value === "") {
        alert("리뷰 내용을 작성해주세요");
        return;
    }

    if(rating_input.value == 0) {
        alert("리뷰 별점을 작성해주세요");
        return;
    }

    const editData = {
        "content" : review_text.value,
        "score" : rating_input.value / 2,
        "reviewId" : currentReviewId
    };

    $.ajax({
        url:"/review/update",
        type: "post",
        data: JSON.stringify(editData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function() {
            location.reload(true);
        },
        error: function(e) {
            alert("리뷰 수정 중 오류가 발생하였습니다");
            console.log(e);
        }
    })
}

function remove(reviewId) {

    const removeData = {
        "reviewId" : reviewId
    }

    $.ajax({
        url:"/review/delete",
        type:"delete",
        data: JSON.stringify(removeData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function() {
            location.reload(true);
        },
        error: function(e) {
            alert("리뷰 삭제 중 오류가 발생하였습니다");
            console.log(e);
        }
    })
}

